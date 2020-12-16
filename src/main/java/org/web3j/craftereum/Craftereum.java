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
    public static final String BINARY = "6080604052600060015534801561001557600080fd5b5060008054600160a060020a03191633179055610942806100376000396000f3fe608060405260043610610097576000357c010000000000000000000000000000000000000000000000000000000090048063c64a038a1161006b578063c64a038a146101bf578063c93db95514610300578063fac333ac1461043a578063fd922a421461048057610097565b8062d624c11461009c57806340e58ee5146100c35780636d87845b146100ef578063a0258d0b14610119575b600080fd5b3480156100a857600080fd5b506100b1610495565b60408051918252519081900360200190f35b3480156100cf57600080fd5b506100ed600480360360208110156100e657600080fd5b503561049b565b005b3480156100fb57600080fd5b506100ed6004803603602081101561011257600080fd5b50356104f4565b6100ed6004803603602081101561012f57600080fd5b81019060208101813564010000000081111561014a57600080fd5b82018360208201111561015c57600080fd5b8035906020019184600183028401116401000000008311171561017e57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610536945050505050565b3480156101cb57600080fd5b506100ed600480360360608110156101e257600080fd5b8135919081019060408101602082013564010000000081111561020457600080fd5b82018360208201111561021657600080fd5b8035906020019184600183028401116401000000008311171561023857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561028b57600080fd5b82018360208201111561029d57600080fd5b803590602001918460018302840111640100000000831117156102bf57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610615945050505050565b34801561030c57600080fd5b506100b16004803603604081101561032357600080fd5b81019060208101813564010000000081111561033e57600080fd5b82018360208201111561035057600080fd5b8035906020019184600183028401116401000000008311171561037257600080fd5b91908080601f01602080910402602001604051908101604052809392919081815260200183838082843760009201919091525092959493602081019350359150506401000000008111156103c557600080fd5b8201836020820111156103d757600080fd5b803590602001918460018302840111640100000000831117156103f957600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610795945050505050565b34801561044657600080fd5b506104646004803603602081101561045d57600080fd5b50356108e2565b60408051600160a060020a039092168252519081900360200190f35b34801561048c57600080fd5b506104646108fd565b60015481565b600081815260026020526040902054600160a060020a031633146104be57600080fd5b6040805182815290517f8bf30e7ff26833413be5f69e1d373744864d600b664204b4a2f9844a8eedb9ed9181900360200190a150565b600054600160a060020a0316331461050b57600080fd5b6000908152600260205260409020805473ffffffffffffffffffffffffffffffffffffffff19169055565b60008054604051600160a060020a03909116913480156108fc02929091818181858888f19350505050158015610570573d6000803e3d6000fd5b507f9060fea662832699cca863409e283b48eb088414dc780c4d43afe69caefee4b534826040518083815260200180602001828103825283818151815260200191508051906020019080838360005b838110156105d75781810151838201526020016105bf565b50505050905090810190601f1680156106045780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150565b600054600160a060020a0316331461062c57600080fd5b60008381526002602090815260408083205490517f1cd7447d00000000000000000000000000000000000000000000000000000000815260048101878152606060248301908152875160648401528751600160a060020a03909416958695631cd7447d958b958b958b95909490936044830193608490930192908801918190849084905b838110156106c85781810151838201526020016106b0565b50505050905090810190601f1680156106f55780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b83811015610728578181015183820152602001610710565b50505050905090810190601f1680156107555780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b15801561077757600080fd5b505af115801561078b573d6000803e3d6000fd5b5050505050505050565b600180548082019091556000818152600260209081526040808320805473ffffffffffffffffffffffffffffffffffffffff1916331790558051848152606081840181815288519183019190915287519495947f69de4c3d3b5253f7bf9b7da430f1dc828dc902164d6882f3d05e08c77206b0b49486948a948a9490939092918401916080850191908701908083838e5b8381101561083e578181015183820152602001610826565b50505050905090810190601f16801561086b5780820380516001836020036101000a031916815260200191505b50838103825284518152845160209182019186019080838360005b8381101561089e578181015183820152602001610886565b50505050905090810190601f1680156108cb5780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a19392505050565b600260205260009081526040902054600160a060020a031681565b600054600160a060020a03168156fea2646970667358221220dfa4b2da2b977f93cb80100d188e1d022c54dcaa674de6a714a4c98f30e00d2264736f6c63430007010033";

    public static final String FUNC__CANCELLED = "_cancelled";

    public static final String FUNC__KILLED = "_killed";

    public static final String FUNC_CANCEL = "cancel";

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
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
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
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.player = (String) eventValues.getNonIndexedValues().get(1).getValue();
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
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.player = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> _cancelled(BigInteger eventid) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC__CANCELLED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(eventid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<TransactionReceipt> cancel(BigInteger eventid) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CANCEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(eventid)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
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

    public RemoteFunctionCall<TransactionReceipt> transfer(String player) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(player)), 
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
        public BigInteger amount;

        public String player;
    }
}
