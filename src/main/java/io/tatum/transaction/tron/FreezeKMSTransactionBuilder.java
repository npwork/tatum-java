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

public class FreezeKMSTransactionBuilder {

    private byte[] from;
    private long duration;
    private int resource;
    private byte[] receiverAddress;

    private long amount;
    private Protocol.Transaction refTransaction;

    private GrpcClient rpcCli;

    private FreezeKMSTransactionBuilder(String fullNode, String solidityNode) {
        this.rpcCli = new GrpcClient(fullNode, solidityNode);
    }

    public static FreezeKMSTransactionBuilder newInstance(String fullNode, String solidityNode) {
        return new FreezeKMSTransactionBuilder(fullNode, solidityNode);
    }

    public FreezeKMSTransactionBuilder from(String from) {
        this.from = WalletApiV2.decodeFromBase58Check(from);
        return this;
    }

    public FreezeKMSTransactionBuilder duration(long duration) {
        this.duration = duration;
        return this;
    }

    public FreezeKMSTransactionBuilder resource(int resource) {
        this.resource = resource;
        return this;
    }

    public FreezeKMSTransactionBuilder to(String to) {
        this.receiverAddress = WalletApiV2.decodeFromBase58Check(to);
        return this;
    }

    public FreezeKMSTransactionBuilder toSun(String amount) {
        this.amount = new BigDecimal(amount).multiply(BigDecimal.valueOf(1_000_000)).longValue();
        return this;
    }

    public FreezeKMSTransactionBuilder build() throws CancelException {
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddress = ByteString.copyFrom(this.from);
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

    public String getTransaction() throws InvalidProtocolBufferException {
        Protocol.Transaction transaction = Protocol.Transaction.parseFrom(refTransaction.toByteArray());
        byte[] rawdata = transaction.getRawData().toByteArray();
        return ByteArray.toHexString(rawdata);
    }

}
