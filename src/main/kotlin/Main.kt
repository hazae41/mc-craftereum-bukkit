package hazae41.craftereum

import net.milkbowl.vault.economy.Economy
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.plugin.java.JavaPlugin
import org.web3j.craftereum.Craftereum
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.DefaultBlockParameterName.LATEST
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.websocket.WebSocketService
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.utils.Convert.Unit.ETHER
import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

class Main : JavaPlugin() {
  val economy = server.servicesManager
    .getRegistration(Economy::class.java)!!
    .provider

  lateinit var web3: Web3j
  lateinit var credentials: Credentials
  lateinit var craftereum: Craftereum

  val listeners = mutableMapOf<BigInteger, Listener>()

  override fun onEnable() {
    super.onEnable()

    saveDefaultConfig()

    credentials = File(dataFolder, "privkey.txt").run {
      if (!exists()) throw Exception("No privkey.txt")
      Credentials.create(readText())
    }

    val url = config.getString("websocket")
      ?: throw Exception("No WebSocket URL")

    val address = config.getString("address")
      ?: throw Exception("No contract address")

    val lastBlock = config.getString("last-block")?.let {
      DefaultBlockParameter.valueOf(BigInteger(it))
    } ?: throw Exception("Invalid last block")

    web3 = Web3j.build(WebSocketService(url, false))

    craftereum = Craftereum.load(address, web3, credentials, DefaultGasProvider())

    craftereum.transferEventFlowable(lastBlock, LATEST)
      .subscribe { onTransferEvent(it) }

    craftereum.onKillEventFlowable(lastBlock, LATEST)
      .subscribe { onKillEvent(it) }

    craftereum.cancelEventFlowable(lastBlock, LATEST)
      .subscribe { onCancelEvent(it) }

    getCommand("craftereum")?.apply {
      setExecutor { sender, _, _, args ->
        if (sender is Player)
          sender.onCommand(args)
        true
      }
    }
  }

  override fun onDisable() {
    super.onDisable()

    web3.shutdown()
  }

  fun transfer(target: String, amount: BigDecimal): TransactionReceipt {
    return Transfer.sendFunds(web3, credentials, target, amount, ETHER).send()
  }

  fun Player.onCommand(args: Array<String>): Boolean {
    val sub = args.getOrNull(0)

    if (sub == "transfer") {
      val target = args.getOrNull(1)
        ?: return false
      val amount = args.getOrNull(2)?.toDoubleOrNull()
        ?: return false

      val res = economy.withdrawPlayer(this, amount)
      if (!res.transactionSuccess()) return true

      try {
        sendMessage("§aPending transaction...")
        val receipt = transfer(target, amount.toBigDecimal())

        if (!receipt.isStatusOK)
          throw Exception(receipt.revertReason)

        sendMessage("§aTransaction done!")
      } catch (e: Exception) {
        logger.warning(e.message)
        sendMessage("§cTransaction failed")
        economy.depositPlayer(this, amount)
      }

      return true
    }

    return false
  }

  fun onCancelEvent(e: Craftereum.CancelEventResponse) {
    val receipt = craftereum._cancelled(e.eventid).send()

    if (!receipt.isStatusOK)
      throw Exception(receipt.revertReason)

    cancel(e.eventid)
  }

  fun onTransferEvent(e: Craftereum.TransferEventResponse) {
    val uuid = UUID.fromString(e.player)
    val player = server.getOfflinePlayer(uuid)

    economy.depositPlayer(player, e.amount.toDouble())

    val currency = economy.currencyNamePlural()
    player.player?.sendMessage("§aYou received ${e.amount} $currency")
  }

  fun onKillEvent(e: Craftereum.OnKillEventResponse) {
    val listener = KillListener(e.eventid, e.killer, e.target)
    server.pluginManager.registerEvents(listener, this)
    listeners[e.eventid] = listener
  }

  fun cancel(eventid: BigInteger) {
    val listener = listeners[eventid]!!
    HandlerList.unregisterAll(listener)
    listeners.remove(eventid)
  }

  inner class KillListener(
    val eventid: BigInteger,
    val killerid: String,
    val targetid: String
  ) : Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    fun onKill(e: PlayerDeathEvent) {
      val target = e.entity
      val killer = e.entity.killer ?: return

      val killerid = killer.uniqueId.toString()
      val targetid = target.uniqueId.toString()

      if (this.killerid.isNotEmpty())
        if (killerid != this.killerid) return
      if (this.targetid.isNotEmpty())
        if (targetid != this.targetid) return

      val receipt = craftereum
        ._killed(eventid, killerid, targetid)
        .send()

      if (!receipt.isStatusOK)
        throw Exception(receipt.revertReason)
    }
  }
}