package org.web3j.listener;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class Listener extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506101bd806100206000396000f3fe608060405234801561001057600080fd5b5060043610610047577c010000000000000000000000000000000000000000000000000000000060003504631cd7447d811461004c575b600080fd5b6101806004803603606081101561006257600080fd5b8135919081019060408101602082013564010000000081111561008457600080fd5b82018360208201111561009657600080fd5b803590602001918460018302840111640100000000831117156100b857600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929594936020810193503591505064010000000081111561010b57600080fd5b82018360208201111561011d57600080fd5b8035906020019184600183028401116401000000008311171561013f57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610182945050505050565b005b50505056fea26469706673582212208daaff180a7c5d0eff0422385806cac7671278fda3cc6963478426df62473a2d64736f6c63430007010033";

    public static final String FUNC_ONKILL = "onkill";

    @Deprecated
    protected Listener(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Listener(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Listener(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Listener(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> onkill(BigInteger _eventid, String _killer, String _target) {
        final Function function = new Function(
                FUNC_ONKILL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_eventid), 
                new org.web3j.abi.datatypes.Utf8String(_killer), 
                new org.web3j.abi.datatypes.Utf8String(_target)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Listener load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Listener(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Listener load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Listener(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Listener load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Listener(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Listener load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Listener(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Listener> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Listener.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Listener> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Listener.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Listener> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Listener.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Listener> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Listener.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
