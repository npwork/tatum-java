package io.tatum.transaction;

import com.google.common.base.Preconditions;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.tatum.blockchain.Tron;
import io.tatum.contracts.trc20.TokenABI;
import io.tatum.contracts.trc20.TokenBytecode;
import io.tatum.model.request.Currency;
import io.tatum.model.request.*;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.transaction.tron.FreezeKMSTransactionBuilder;
import io.tatum.transaction.tron.FreezeTransactionBuilder;
import io.tatum.transaction.tron.TronKMSTransactionBuilder;
import io.tatum.transaction.tron.TronTransactionBuilder;
import io.tatum.utils.ObjectValidator;
import org.junit.Assert;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.ByteUtil;
import org.tron.common.utils.Hash;
import org.tron.core.exception.CancelException;
import org.tron.tronj.abi.TypeReference;
import org.tron.tronj.abi.datatypes.Address;
import org.tron.tronj.abi.datatypes.Bool;
import org.tron.tronj.abi.datatypes.Function;
import org.tron.tronj.abi.datatypes.generated.Uint256;
import org.tron.tronj.client.TronClient;
import org.tron.tronj.client.exceptions.IllegalException;
import org.tron.tronj.client.transaction.TransactionBuilder;
import org.tron.tronj.proto.Chain;
import org.tron.tronj.proto.Contract;
import org.tron.tronj.proto.Response;
import org.tron.tronj.utils.Base58Check;
import org.tron.tronj.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TronTx {

    /**
     * Send Tron transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendTronTransaction(boolean testnet, TransferTron body) throws IOException, InterruptedException, ExecutionException {
        return new Tron().tronBroadcast(prepareTronSignedTransaction(testnet, body), null);
    }

    /**
     * Send Tron Freeze balance transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash freezeTronTransaction(boolean testnet, FreezeTron body) throws IOException, CancelException, InterruptedException, ExecutionException {
        return new Tron().tronBroadcast(prepareTronFreezeTransaction(testnet, body), null);
    }

    /**
     * Send Tron TRC10 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendTronTrc10Transaction(boolean testnet, TransferTronTrc10 body, int precision) throws IllegalException, InterruptedException, ExecutionException, IOException {
        return new Tron().tronBroadcast(prepareTronTrc10SignedTransaction(testnet, body, precision), null);
    }

    /**
     * Send Tron TRC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendTronTrc20Transaction(boolean testnet, TransferTronTrc20 body) throws InterruptedException, ExecutionException, IOException {
        return new Tron().tronBroadcast(prepareTronTrc20SignedTransaction(testnet, body), null);
    }

    /**
     * Create Tron TRC10 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash createTronTrc10Transaction(boolean testnet, CreateTronTrc10 body) throws IllegalException, InterruptedException, ExecutionException, IOException {
        return new Tron().tronBroadcast(prepareTronCreateTrc10SignedTransaction(testnet, body), null);
    }

    /**
     * Create Tron TRC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns TransactionHash of the transaction in the blockchain
     */
    public TransactionHash createTronTrc20Transaction(boolean testnet, CreateTronTrc20 body) throws Exception {
        return new Tron().tronBroadcast(prepareTronCreateTrc20SignedTransaction(testnet, body), null);
    }

    /**
     * Sign Tron transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronSignedTransaction(boolean testnet, TransferTron body) throws InvalidProtocolBufferException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        String fullNode;
        String solidityNode;
        if (testnet) {
            fullNode = "grpc.shasta.trongrid.io:50051";
            solidityNode = "grpc.shasta.trongrid.io:50052";
        } else {
            fullNode = "grpc.trongrid.io:50051";
            solidityNode = "grpc.trongrid.io:50052";
        }

        TronTransactionBuilder builder = TronTransactionBuilder.
                newInstance(fullNode, solidityNode).
                from(body.getFromPrivateKey()).
                to(body.getTo()).
                toSun(body.getAmount()).
                build();
        return ByteArray.toHexString(builder.sign());
    }

    /**
     * Sign Tron Freeze balance transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronFreezeTransaction(boolean testnet, FreezeTron body) throws InvalidProtocolBufferException, CancelException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        String fullNode;
        String solidityNode;
        if (testnet) {
            fullNode = "grpc.shasta.trongrid.io:50051";
            solidityNode = "grpc.shasta.trongrid.io:50052";
        } else {
            fullNode = "grpc.trongrid.io:50051";
            solidityNode = "grpc.trongrid.io:50052";
        }

        FreezeTransactionBuilder builder = FreezeTransactionBuilder.
                newInstance(fullNode, solidityNode).
                from(body.getFromPrivateKey()).
                to(body.getReceiver()).
                resource(body.getResource().getResource()).
                duration(body.getDuration()).
                toSun(body.getAmount()).
                build();
        return ByteArray.toHexString(builder.sign());
    }

    /**
     * Sign Tron TRC10 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronTrc10SignedTransaction(boolean testnet, TransferTronTrc10 body, int precision) throws IllegalException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        TronClient client = testnet ? TronClient.ofShasta(body.getFromPrivateKey()) : TronClient.ofMainnet(body.getFromPrivateKey());

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String fromAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        Response.TransactionExtention transactionExtention = client.transferTrc10(fromAddress, body.getTo(),
                Integer.valueOf(body.getTo()), Long.valueOf(body.getAmount()));
        Chain.Transaction signedTxn = client.signTransaction(transactionExtention);
        System.out.println(signedTxn.toString());
        return ByteArray.toHexString(signedTxn.toByteArray());
    }

    /**
     * Sign Tron TRC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronTrc20SignedTransaction(boolean testnet, TransferTronTrc20 body) {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        TronClient client = testnet ? TronClient.ofShasta(body.getFromPrivateKey()) : TronClient.ofMainnet(body.getFromPrivateKey());

        BigInteger scalingFactorBi = getScalingFactorBi(client, Base58Check.base58ToBytes(body.getTokenAddress()));
        BigInteger decimals = new BigInteger(body.getAmount()).multiply(BigInteger.TEN.pow(scalingFactorBi.intValue()));

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String ownerAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        // transfer(address,uint256) returns (bool)
        Function transfer = new Function("transfer",
                Arrays.asList(new Address(body.getTo()),
                        new Uint256(decimals)),
                Collections.singletonList(new TypeReference<Bool>() {
                })
        );

        TransactionBuilder builder = client.triggerCall(ownerAddress, body.getTokenAddress(), transfer);
        builder.setFeeLimit(body.getFeeLimit());
        Chain.Transaction signedTxn = client.signTransaction(builder.build());
        System.out.println(signedTxn.getRawData().getData());
        return ByteArray.toHexString(signedTxn.toByteArray());
    }

    /**
     * Sign create Tron TRC10 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronCreateTrc10SignedTransaction(boolean testnet, CreateTronTrc10 body) throws IllegalException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        TronClient client = testnet ? TronClient.ofShasta(body.getFromPrivateKey()) : TronClient.ofMainnet(body.getFromPrivateKey());

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String ownerAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        HashMap<String, String> frozenSupply = new HashMap<>();
        frozenSupply.put("0", "0");

        Response.TransactionExtention txExt = client.createAssetIssue(ownerAddress, body.getName(), body.getAbbreviation(), body.getTotalSupply(), 0,
                0, new Date(System.currentTimeMillis() + 60_000L).getTime(),
                new Date(System.currentTimeMillis() + 100_000L).getTime(), body.getUrl(),
                0, 0, body.getDecimals(), frozenSupply, body.getDescription());

        Chain.Transaction signedTxn = client.signTransaction(txExt);
        System.out.println(signedTxn.toString());
        return ByteArray.toHexString(signedTxn.toByteArray());

    }

    /**
     * Sign create Tron TRC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronCreateTrc20SignedTransaction(boolean testnet, CreateTronTrc20 body) throws Exception {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        TronClient client;
        if (testnet) {
            client = TronClient.ofShasta(body.getFromPrivateKey());
        } else {
            client = TronClient.ofMainnet(body.getFromPrivateKey());
        }

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String ownerAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        org.tron.tronj.client.contract.Contract cntr = new org.tron.tronj.client.contract.Contract.Builder()
                .setOwnerAddr(TronClient.parseAddress(ownerAddress))
                .setOriginAddr(TronClient.parseAddress(ownerAddress))
                .setBytecode(ByteString.copyFrom(Numeric.hexStringToByteArray(TokenBytecode.TOKEN_BYTE_CODE)))
                .setAbi(TokenABI.TOKEN_ABI)
                .setCallValue(0)
                .setName(body.getName())
                .setConsumeUserResourcePercent(100)
                .setOriginEnergyLimit(1)
                .build();

        cntr.setClient(client);

        TransactionBuilder builder = cntr.deploy();
        //use the following method with parameters to call if has any TRC-10 deposit
        //TransactionBuilder builder = cntr.deploy(tokenId, callTokenValue);
        builder.setFeeLimit(1000_000_000L);
        //sign transaction
        Chain.Transaction signedTxn = client.signTransaction(builder.build());
        System.out.println(signedTxn.toString());

        return ByteArray.toHexString(signedTxn.toByteArray());

    }

    /**
     * Prepare Tron transaction for KMS. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronSignedKMSTransaction(boolean testnet, TransferTron body) throws InvalidProtocolBufferException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        String fullNode;
        String solidityNode;
        if (testnet) {
            fullNode = "grpc.shasta.trongrid.io:50051";
            solidityNode = "grpc.shasta.trongrid.io:50052";
        } else {
            fullNode = "grpc.trongrid.io:50051";
            solidityNode = "grpc.trongrid.io:50052";
        }

        TronKMSTransactionBuilder builder = TronKMSTransactionBuilder.
                newInstance(fullNode, solidityNode).
                from(body.getFrom()).
                to(body.getTo()).
                toSun(body.getAmount()).
                build();

        return builder.getTransaction();
    }

    /**
     * Prepare Tron Freeze balance transaction for KMS. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronFreezeKMSTransaction(boolean testnet, FreezeTron body) throws CancelException, InvalidProtocolBufferException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        String fullNode;
        String solidityNode;
        if (testnet) {
            fullNode = "grpc.shasta.trongrid.io:50051";
            solidityNode = "grpc.shasta.trongrid.io:50052";
        } else {
            fullNode = "grpc.trongrid.io:50051";
            solidityNode = "grpc.trongrid.io:50052";
        }

        FreezeKMSTransactionBuilder builder = FreezeKMSTransactionBuilder.
                newInstance(fullNode, solidityNode).
                from(body.getFromPrivateKey()).
                to(body.getReceiver()).
                resource(body.getResource().getResource()).
                duration(body.getDuration()).
                toSun(body.getAmount()).
                build();

        return builder.getTransaction();
    }

    /**
     * Prepare Tron TRC10 transaction for KMS. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronTrc10SignedKMSTransaction(boolean testnet, TransferTronTrc10 body) throws IllegalException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        TronClient client = testnet ? TronClient.ofShasta(body.getFromPrivateKey()) : TronClient.ofMainnet(body.getFromPrivateKey());

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String fromAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        Response.TransactionExtention transactionExtention = client.transferTrc10(fromAddress, body.getTo(),
                Integer.parseInt(body.getTo()), Long.parseLong(body.getAmount()));

        return ByteArray.toHexString(transactionExtention.toByteArray());
    }

    /**
     * Prepare Tron TRC20 transaction for KMS. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronTrc20SignedKMSTransaction(boolean testnet, TransferTronTrc20 body) {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        TronClient client = testnet ? TronClient.ofShasta(body.getFromPrivateKey()) : TronClient.ofMainnet(body.getFromPrivateKey());

        BigInteger scalingFactorBi = getScalingFactorBi(client, Base58Check.base58ToBytes(body.getTokenAddress()));
        BigInteger decimals = new BigInteger(body.getAmount()).multiply(BigInteger.TEN.pow(scalingFactorBi.intValue()));

        // transfer(address,uint256) returns (bool)
        Function transfer = new Function("transfer",
                Arrays.asList(new Address(body.getTo()), new Uint256(decimals)),
                Collections.singletonList(new TypeReference<Bool>() {
                })
        );

        TransactionBuilder builder = client.triggerCall(body.getFrom(), body.getTokenAddress(), transfer);
        builder.setFeeLimit(body.getFeeLimit());
        Chain.Transaction signedTxn = client.signTransaction(builder.build());
        System.out.println(signedTxn.getRawData().getData());
        return ByteArray.toHexString(signedTxn.toByteArray());
    }

    /**
     * Prepare create Tron TRC10 transaction for KMS. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronCreateTrc10SignedKMSTransaction(boolean testnet, CreateTronTrc10 body) throws IllegalException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        TronClient client = testnet ? TronClient.ofShasta(body.getFromPrivateKey()) : TronClient.ofMainnet(body.getFromPrivateKey());

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String ownerAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        HashMap<String, String> frozenSupply = new HashMap<>();
        frozenSupply.put("0", "0");

        Response.TransactionExtention txExt = client.createAssetIssue(ownerAddress, body.getName(), body.getAbbreviation(), body.getTotalSupply(), 0,
                0, new Date(System.currentTimeMillis() + 60_000L).getTime(),
                new Date(System.currentTimeMillis() + 100_000L).getTime(), body.getUrl(),
                0, 0, body.getDecimals(), frozenSupply, body.getDescription());

        return ByteArray.toHexString(txExt.toByteArray());
    }

    private static BigInteger getScalingFactorBi(TronClient client, byte[] contractAddress) {
        byte[] scalingFactorBytes = triggerGetScalingFactor(client, contractAddress);
        return ByteUtil.bytesToBigInteger(scalingFactorBytes);
    }

    /**
     * Prepare create Tron TRC20 transaction for KMS. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareTronCreateTrc20SignedKMSTransaction(boolean testnet, CreateTronTrc20 body) throws Exception {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        TronClient client;
        if (testnet) {
            client = TronClient.ofShasta(body.getFromPrivateKey());
        } else {
            client = TronClient.ofMainnet(body.getFromPrivateKey());
        }

        byte[] privateKey = ByteArray.fromHexString(body.getFromPrivateKey());
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        String ownerAddress = Base58Check.bytesToBase58(ecKey.getAddress());

        org.tron.tronj.client.contract.Contract cntr = new org.tron.tronj.client.contract.Contract.Builder()
                .setOwnerAddr(TronClient.parseAddress(ownerAddress))
                .setOriginAddr(TronClient.parseAddress(ownerAddress))
                .setBytecode(ByteString.copyFrom(Numeric.hexStringToByteArray(TokenBytecode.TOKEN_BYTE_CODE)))
                .setAbi(TokenABI.TOKEN_ABI)
                .setCallValue(0)
                .setName(body.getName())
                .setConsumeUserResourcePercent(100)
                .setOriginEnergyLimit(1)
                .build();

        cntr.setClient(client);

        TransactionBuilder builder = cntr.deploy();
        //use the following method with parameters to call if has any TRC-10 deposit
        //TransactionBuilder builder = cntr.deploy(tokenId, callTokenValue);
        builder.setFeeLimit(1000_000_000L);

        return ByteArray.toHexString(builder.getTransaction().toByteArray());
    }

    /**
     * Sign Tron pending transaction from Tatum KMS
     *
     * @param tx             pending transaction from KMS
     * @param fromPrivateKey private key to sign transaction with.
     * @param testnet        mainnet or testnet version
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signTrxKMSTransaction(TransactionKMS tx, String fromPrivateKey, boolean testnet) throws Exception {
        if (tx.getChain() != Currency.TRON) {
            throw new Exception("Unsupported chain.");
        }

        Chain.Transaction.raw raw = Chain.Transaction.raw.parseFrom(ByteArray.fromHexString(tx.getSerializedTransaction()));
        Chain.Transaction transaction = Chain.Transaction.newBuilder().build().toBuilder().setRawData(raw).build();

        TronClient client;
        if (testnet) {
            client = TronClient.ofShasta(fromPrivateKey);
        } else {
            client = TronClient.ofMainnet(fromPrivateKey);
        }

        Chain.Transaction signedTxn = client.signTransaction(transaction);
        System.out.println(signedTxn.toString());

        return ByteArray.toHexString(signedTxn.toByteArray());
    }

    private static byte[] triggerGetScalingFactor(TronClient client, byte[] contractAddress) {
        String methodSign = "decimals()";
        byte[] selector = new byte[4];
        System.arraycopy(Hash.sha3(methodSign.getBytes()), 0, selector, 0, 4);

        Contract.TriggerSmartContract triggerBuilder = Contract.TriggerSmartContract.newBuilder()
                .setContractAddress(ByteString.copyFrom(contractAddress))
                .setData(ByteString.copyFrom(selector))
                .build();

        Response.TransactionExtention trxExt2 = client.blockingStub.triggerConstantContract(triggerBuilder);

        List<ByteString> list = trxExt2.getConstantResultList();
        byte[] result = new byte[0];
        for (ByteString bs : list) {
            result = ByteUtil.merge(result, bs.toByteArray());
        }
        Assert.assertEquals(32, result.length);
        System.out.println(ByteArray.toHexString(result));
        return result;
    }
}
