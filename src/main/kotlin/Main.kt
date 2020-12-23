package hazae41.craftereum

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
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
import org.web3j.protocol.websocket.WebSocketService
import org.web3j.tx.gas.DefaultGasProvider
import java.io.File
import java.math.BigInteger
import java.util.*

fun String.toUUID() = try {
  UUID.fromString(this)
} catch (e: Exception) {
  null
}

fun blockParam(number: Long) =
  DefaultBlockParameter.valueOf(BigInteger.valueOf(number))

fun blockParam(number: String) =
  DefaultBlockParameter.valueOf(BigInteger(number))

class Main : JavaPlugin() {

  lateinit var web3: Web3j
  lateinit var craftereum: Craftereum
  lateinit var economy: Economy

  val listeners = mutableMapOf<Long, Listener>()

  override fun onEnable() {
    super.onEnable()

    saveDefaultConfig()

    economy = server.servicesManager
      .getRegistration(Economy::class.java)!!
      .provider

    val credentials = File(dataFolder, "privkey.txt").run {
      if (!exists()) throw Exception("No privkey.txt")
      Credentials.create(readText())
    }

    val url = config.getString("address")
      ?: throw Exception("No WebSocket URL")

    val address = config.getString("contract")
      ?: throw Exception("No contract address")

    val lastBlock = config.getString("last-block")
      ?.let { blockParam(it) }
      ?: throw Exception("Invalid last block")

    val webSocket = WebSocketService(url, false)
      .apply { connect() }

    logger.info("Connected")

    web3 = Web3j.build(webSocket)

    craftereum = Craftereum.load(address, web3, credentials, DefaultGasProvider())

    craftereum.transferEventFlowable(lastBlock, LATEST)
      .subscribe { onTransferEvent(it) }

    craftereum.onKillEventFlowable(blockParam(0), LATEST)
      .subscribe { onKillEvent(it) }

    craftereum.cancelEventFlowable(blockParam(0), LATEST)
      .subscribe { cancel(it.eventid) }

    getCommand("craftereum")?.apply {
      setExecutor { sender, _, _, args ->
        if (sender is Player)
          onCommand(sender, args)
        true
      }
    }
  }

  fun setLastBlock(number: BigInteger) {
    config.set("last-block", number.toString())
    saveConfig()
  }

  override fun onDisable() {
    super.onDisable()

    web3.shutdown()
  }

  fun onCommand(player: Player, args: Array<String>): Boolean {
    val sub = args.getOrNull(0)

    if (sub == "transfer") {
      val target = args.getOrNull(1)
        ?: return false
      val amount = args.getOrNull(2)?.toIntOrNull()
        ?: return false

      GlobalScope.launch(IO) {
        try {
          val res = economy.withdrawPlayer(player, amount.toDouble())
          if (!res.transactionSuccess()) return@launch

          player.sendMessage("§aPending transaction...")

          val receipt = craftereum
            ._transfer(target, amount.toBigInteger())
            .sendAsync().await()

          if (!receipt.isStatusOK)
            throw Exception(receipt.revertReason)

          player.sendMessage("§aTransaction done!")

        } catch (e: Exception) {
          logger.warning(e.message)
          player.sendMessage("§cTransaction failed")
          economy.depositPlayer(player, amount.toDouble())
        }
      }

      return true
    }

    return false
  }

  fun onTransferEvent(e: Craftereum.TransferEventResponse) {
    setLastBlock(e.log.blockNumber)

    val uuid = e.player.toUUID() ?: return
    val player = server.getOfflinePlayer(uuid)
    economy.depositPlayer(player, e.amount.toDouble())
    println("Transferred ${e.amount} to ${e.player}")

    val currency = economy.currencyNamePlural()
    player.player?.sendMessage("§aYou received ${e.amount} $currency")
  }

  fun onKillEvent(e: Craftereum.OnKillEventResponse) {
    val listener = KillListener(e.eventid, e.killer, e.target)
    server.pluginManager.registerEvents(listener, this)
    listeners[e.eventid.toLong()] = listener
    println("Registered kill listener ${e.eventid}")
  }

  fun cancel(eventid: BigInteger) {
    val listener = listeners[eventid.toLong()]
      ?: return

    HandlerList.unregisterAll(listener)
    listeners.remove(eventid.toLong())
    println("Cancelled listener $eventid")
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

      GlobalScope.launch(IO) {
        val receipt = craftereum
          ._killed(eventid, killerid, targetid)
          .sendAsync().await()

        if (!receipt.isStatusOK)
          throw Exception(receipt.revertReason)
      }
    }
  }
}