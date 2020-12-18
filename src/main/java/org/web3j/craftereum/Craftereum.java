package org.web3j.craftereum;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.8.1.
 */
@SuppressWarnings("rawtypes")
public class Craftereum extends Contract {
    public static final String BINARY = "6080604052600060025534801561001557600080fd5b5060018054600160a060020a03191633179055610a51806100376000396000f3fe608060405234801561001057600080fd5b50600436106100af576000357c01000000000000000000000000000000000000000000000000000000009004806360c4c6ab1161008357806360c4c6ab146101c1578063c64a038a146101e5578063c93db95514610319578063fac333ac14610446578063fd922a4214610463576100af565b8062d624c1146100b457806312cb70c7146100ce57806340e58ee5146101785780634847a79c14610195575b600080fd5b6100bc61046b565b60408051918252519081900360200190f35b610176600480360360408110156100e457600080fd5b8101906020810181356401000000008111156100ff57600080fd5b82018360208201111561011157600080fd5b8035906020019184600183028401116401000000008311171561013357600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505091359250610471915050565b005b6101766004803603602081101561018e57600080fd5b50356105b9565b610176600480360360408110156101ab57600080fd5b50600160a060020a0381351690602001356105e8565b6101c96106aa565b60408051600160a060020a039092168252519081900360200190f35b610176600480360360608110156101fb57600080fd5b8135919081019060408101602082013564010000000081111561021d57600080fd5b82018360208201111561022f57600080fd5b8035906020019184600183028401116401000000008311171561025157600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156102a457600080fd5b8201836020820111156102b657600080fd5b803590602001918460018302840111640100000000831117156102d857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295506106b9945050505050565b6100bc6004803603604081101561032f57600080fd5b81019060208101813564010000000081111561034a57600080fd5b82018360208201111561035c57600080fd5b8035906020019184600183028401116401000000008311171561037e57600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156103d157600080fd5b8201836020820111156103e357600080fd5b8035906020019184600183028401116401000000008311171561040557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610842945050505050565b6101c96004803603602081101561045c57600080fd5b5035610990565b6101c96109ab565b60025481565b60008054604080517f9dc29fac000000000000000000000000000000000000000000000000000000008152336004820152602481018590529051600160a060020a0390921692639dc29fac926044808401936020939083900390910190829087803b1580156104df57600080fd5b505af11580156104f3573d6000803e3d6000fd5b505050506040513d602081101561050957600080fd5b505161051457600080fd5b7fcc3a7de252b5f0a9ec0225e4f6e85dbe6de549ab6f3749c6ef9db89df8f6ac2682826040518080602001838152602001828103825284818151815260200191508051906020019080838360005b8381101561057a578181015183820152602001610562565b50505050905090810190601f1680156105a75780820380516001836020036101000a031916815260200191505b50935050505060405180910390a15050565b600081815260036020526040902054600160a060020a031633146105dc57600080fd5b6105e5816109ba565b50565b600154600160a060020a031633146105ff57600080fd5b60008054604080517f40c10f19000000000000000000000000000000000000000000000000000000008152600160a060020a03868116600483015260248201869052915191909216926340c10f1992604480820193602093909283900390910190829087803b15801561067157600080fd5b505af1158015610685573d6000803e3d6000fd5b505050506040513d602081101561069b57600080fd5b50516106a657600080fd5b5050565b600054600160a060020a031681565b600154600160a060020a031633146106d057600080fd5b60008381526003602090815260408083205490517f1cd7447d00000000000000000000000000000000000000000000000000000000815260048101878152606060248301908152875160648401528751600160a060020a03909416958695631cd7447d958b958b958b95909490936044830193608490930192908801918190849084905b8381101561076c578181015183820152602001610754565b50505050905090810190601f1680156107995780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b838110156107cc5781810151838201526020016107b4565b50505050905090810190601f1680156107f95780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b15801561081b57600080fd5b505af115801561082f573d6000803e3d6000fd5b5050505061083c846109ba565b50505050565b60028054600181019091556000818152600360209081526040808320805473ffffffffffffffffffffffffffffffffffffffff1916331790558051848152606081840181815288519183019190915287519495947f69de4c3d3b5253f7bf9b7da430f1dc828dc902164d6882f3d05e08c77206b0b49486948a948a9490939092918401916080850191908701908083838e5b838110156108ec5781810151838201526020016108d4565b50505050905090810190601f1680156109195780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561094c578181015183820152602001610934565b50505050905090810190601f1680156109795780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a19392505050565b600360205260009081526040902054600160a060020a031681565b600154600160a060020a031681565b600081815260036020908152604091829020805473ffffffffffffffffffffffffffffffffffffffff19169055815183815291517f8bf30e7ff26833413be5f69e1d373744864d600b664204b4a2f9844a8eedb9ed9281900390910190a15056fea26469706673582212203ce5121e1d3b3007d14637f1d7b77d34d576a055d3716ea77503495bdd0fb48f64736f6c63430007010033";

    public static final String FUNC__KILLED = "_killed";

    public static final String FUNC__TRANSFER = "_transfer";

    public static final String FUNC_CANCEL = "cancel";

    public static final String FUNC_EMERALDS = "emeralds";

    public static final String FUNC_IDS = "ids";

    public static final String FUNC_LASTID = "lastid";

    public static final String FUNC_ONKILL = "onkill";

    public static final String FUNC_SERVER = "server";

    public static final String FUNC_TRANSFER = "transfer";

    public static final Event CANCEL_EVENT = new Event("Cancel", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event ONKILL_EVENT = new Event("OnKill", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Craftereum(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Craftereum(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Craftereum(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Craftereum(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CancelEventResponse> getCancelEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CANCEL_EVENT, transactionReceipt);
        ArrayList<CancelEventResponse> responses = new ArrayList<CancelEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CancelEventResponse typedResponse = new CancelEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.eventid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CancelEventResponse>() {
            @Override
            public CancelEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CANCEL_EVENT, log);
                CancelEventResponse typedResponse = new CancelEventResponse();
                typedResponse.log = log;
                typedResponse.eventid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CancelEventResponse> cancelEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANCEL_EVENT));
        return cancelEventFlowable(filter);
    }

    public List<OnKillEventResponse> getOnKillEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ONKILL_EVENT, transactionReceipt);
        ArrayList<OnKillEventResponse> responses = new ArrayList<OnKillEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OnKillEventResponse typedResponse = new OnKillEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.eventid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.killer = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.target = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OnKillEventResponse> onKillEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OnKillEventResponse>() {
            @Override
            public OnKillEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ONKILL_EVENT, log);
                OnKillEventResponse typedResponse = new OnKillEventResponse();
                typedResponse.log = log;
                typedResponse.eventid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.killer = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.target = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OnKillEventResponse> onKillEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ONKILL_EVENT));
        return onKillEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.player = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse.player = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> _killed(BigInteger eventid, String killer, String target) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC__KILLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(eventid), 
                new org.web3j.abi.datatypes.Utf8String(killer), 
                new org.web3j.abi.datatypes.Utf8String(target)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> _transfer(String account, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC__TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, account), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> cancel(BigInteger eventid) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(eventid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> emeralds() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EMERALDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> ids(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_IDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> lastid() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LASTID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> onkill(String killer, String target) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ONKILL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(killer), 
                new org.web3j.abi.datatypes.Utf8String(target)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> server() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_SERVER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String player, BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(player), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Craftereum load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Craftereum(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Craftereum load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Craftereum(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Craftereum load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Craftereum(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Craftereum load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Craftereum(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Craftereum> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Craftereum.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<Craftereum> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Craftereum.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Craftereum> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Craftereum.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Craftereum> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Craftereum.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class CancelEventResponse extends BaseEventResponse {
        public BigInteger eventid;
    }

    public static class OnKillEventResponse extends BaseEventResponse {
        public BigInteger eventid;

        public String killer;

        public String target;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String player;

        public BigInteger amount;
    }
}
