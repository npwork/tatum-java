package io.tatum.transaction.tron;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.tron.api.GrpcAPI;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Hash;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.core.exception.CancelException;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.protos.contract.SmartContractOuterClass;
import org.tron.walletserver.GrpcClient;
import org.tron.walletserver.WalletApiV2;

import java.math.BigDecimal;

public class FreezeTransactionBuilder {

    private String fullNode;
    private String solidityNode;

    private byte[] ownerAddress;
    private long duration;
    private int resource;
    private byte[] receiverAddress;

    private byte[] privateKey;
    private long amount;
    private Protocol.Transaction refTransaction;

    private GrpcClient rpcCli;

    private FreezeTransactionBuilder(String fullNode, String solidityNode) {
        this.fullNode = fullNode;
        this.solidityNode = solidityNode;
        this.rpcCli = new GrpcClient(fullNode, solidityNode);
    }

    public static FreezeTransactionBuilder newInstance(String fullNode, String solidityNode) {
        return new FreezeTransactionBuilder(fullNode, solidityNode);
    }

    public FreezeTransactionBuilder from(String from) {
        this.privateKey = ByteArray.fromHexString(from);
        ECKey ecKey = ECKey.fromPrivate(this.privateKey);
        this.ownerAddress = ecKey.getAddress();
        return this;
    }

    public FreezeTransactionBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    public FreezeTransactionBuilder resource(int resource) {
        this.resource = resource;
        return this;
    }

    public FreezeTransactionBuilder to(String to) {
        this.receiverAddress = WalletApiV2.decodeFromBase58Check(to);
        return this;
    }

    public FreezeTransactionBuilder toSun(String amount) {
        this.amount = new BigDecimal(amount).multiply(BigDecimal.valueOf(1_000_000)).longValue();
        return this;
    }

    public FreezeTransactionBuilder build() throws CancelException {
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddress = ByteString.copyFrom(this.ownerAddress);
        builder.setOwnerAddress(byteAddress)
                .setFrozenBalance(amount)
                .setFrozenDuration(duration)
                .setResourceValue(resource);

        if (receiverAddress != null) {
            ByteString receiverAddressBytes = ByteString.copyFrom(receiverAddress);
            builder.setReceiverAddress(receiverAddressBytes);
        }

        BalanceContract.FreezeBalanceContract contract = builder.build();

        GrpcAPI.TransactionExtention transactionExtention = rpcCli.createTransaction2(contract);

        Protocol.Transaction transaction = transactionExtention.getTransaction();

        if (transaction.getRawData().getTimestamp() == 0) {

            transaction = TransactionUtils.setTimestamp(transaction);
        }
        transaction = TransactionUtils.setExpirationTime(transaction);
        transaction = TransactionUtils.setDefaultPermissionId(transaction);
        this.refTransaction = transaction;

        return this;
    }

    public byte[] sign() throws InvalidProtocolBufferException, CancelException {
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        Protocol.Transaction transaction = Protocol.Transaction.parseFrom(refTransaction.toByteArray());
        byte[] rawdata = transaction.getRawData().toByteArray();
        byte[] hash = Sha256Sm3Hash.hash(rawdata);
        byte[] sign = ecKey.sign(hash).toByteArray();
        transaction = transaction.toBuilder().addSignature(ByteString.copyFrom(sign)).build();

//        GrpcAPI.TransactionSignWeight weight = rpcCli.getTransactionSignWeight(transaction);
//        if (weight.getResult().getCode() != GrpcAPI.TransactionSignWeight.Result.response_code.ENOUGH_PERMISSION) {
//            throw new CancelException("User cancelled");
//        }
        showTransactionAfterSign(transaction);
        return transaction.toByteArray();
    }

    public String getTransaction() {
        return ByteArray.toHexString(this.refTransaction.toByteArray());
    }

    private void showTransactionAfterSign(Protocol.Transaction transaction)
            throws InvalidProtocolBufferException {
        System.out.println("after sign transaction hex string is " +
                ByteArray.toHexString(transaction.toByteArray()));
        System.out.println("txid is " +
                ByteArray.toHexString(Sha256Sm3Hash.hash(transaction.getRawData().toByteArray())));

        if (transaction.getRawData().getContract(0).getType() == Protocol.Transaction.Contract.ContractType.CreateSmartContract) {
            SmartContractOuterClass.CreateSmartContract createSmartContract = transaction.getRawData().getContract(0)
                    .getParameter().unpack(SmartContractOuterClass.CreateSmartContract.class);
            byte[] contractAddress = generateContractAddress(
                    createSmartContract.getOwnerAddress().toByteArray(), transaction);
            System.out.println(
                    "Your smart contract address will be: " + WalletApiV2.encode58Check(contractAddress));
        }
    }

    private byte[] generateContractAddress(byte[] ownerAddress, Protocol.Transaction trx) {
        // get tx hash
        byte[] txRawDataHash = Sha256Sm3Hash.of(trx.getRawData().toByteArray()).getBytes();

        // combine
        byte[] combined = new byte[txRawDataHash.length + ownerAddress.length];
        System.arraycopy(txRawDataHash, 0, combined, 0, txRawDataHash.length);
        System.arraycopy(ownerAddress, 0, combined, txRawDataHash.length, ownerAddress.length);

        return Hash.sha3omit12(combined);
    }
}
