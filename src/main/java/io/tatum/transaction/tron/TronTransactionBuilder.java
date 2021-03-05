package io.tatum.transaction.tron;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.walletserver.GrpcClient;
import org.tron.walletserver.WalletApiV2;

import java.math.BigDecimal;

public class TronTransactionBuilder {

    private String fullNode;
    private String solidityNode;

    private byte[] privateKey;
    private byte[] from;
    private byte[] to;
    private long amount;
    private Protocol.Transaction refTransaction;

    private GrpcClient rpcCli;

    private TronTransactionBuilder(String fullNode, String solidityNode) {
        this.fullNode = fullNode;
        this.solidityNode = solidityNode;
        this.rpcCli = new GrpcClient(fullNode, solidityNode);
    }

    public static TronTransactionBuilder newInstance(String fullNode, String solidityNode) {
        return new TronTransactionBuilder(fullNode, solidityNode);
    }

    public TronTransactionBuilder from(String from) {
        this.privateKey = ByteArray.fromHexString(from);
        ECKey ecKey = ECKey.fromPrivate(this.privateKey);
        this.from = ecKey.getAddress();
        return this;
    }

    public TronTransactionBuilder to(String to) {
        this.to = WalletApiV2.decodeFromBase58Check(to);
        return this;
    }

    public TronTransactionBuilder toSun(String amount) {
        this.amount = new BigDecimal(amount).multiply(BigDecimal.valueOf(1_000_000)).longValue();
        return this;
    }

    public TronTransactionBuilder build() {

        Protocol.Transaction.Builder transactionBuilder = Protocol.Transaction.newBuilder();

        Protocol.Block newestBlock = rpcCli.getBlock(-1);

        Protocol.Transaction.Contract.Builder contractBuilder = Protocol.Transaction.Contract.newBuilder();
        BalanceContract.TransferContract.Builder transferContractBuilder = BalanceContract.TransferContract.newBuilder();
        transferContractBuilder.setAmount(amount);

        ByteString bsTo = ByteString.copyFrom(to);

        ByteString bsOwner = ByteString.copyFrom(from);

        transferContractBuilder.setToAddress(bsTo);

        transferContractBuilder.setOwnerAddress(bsOwner);
        try {
            Any any = Any.pack(transferContractBuilder.build());
            contractBuilder.setParameter(any);
        } catch (Exception e) {
            return null;
        }
        contractBuilder.setType(Protocol.Transaction.Contract.ContractType.TransferContract);

        System.out.println("======================= Raw Transaction Data ========================================");
        System.out.println(ByteArray.toHexString(transactionBuilder.
                getRawDataBuilder().
                addContract(contractBuilder).
                build().toByteArray()));

        System.out.println(transactionBuilder.
                getRawDataBuilder().
                addContract(contractBuilder).
                build().toString());

        transactionBuilder.getRawDataBuilder().addContract(contractBuilder)
                .setTimestamp(System.currentTimeMillis())
                .setExpiration(newestBlock.getBlockHeader().getRawData().getTimestamp() + 10 * 60 * 60 * 1000);
        Protocol.Transaction transaction = transactionBuilder.build();
        this.refTransaction = setReference(transaction, newestBlock);
        return this;
    }

    private static Protocol.Transaction setReference(Protocol.Transaction transaction, Protocol.Block newestBlock) {
        long blockHeight = newestBlock.getBlockHeader().getRawData().getNumber();
        byte[] blockHash = getBlockHash(newestBlock).getBytes();
        byte[] refBlockNum = ByteArray.fromLong(blockHeight);
        Protocol.Transaction.raw rawData = transaction.getRawData().toBuilder()
                .setRefBlockHash(ByteString.copyFrom(ByteArray.subArray(blockHash, 8, 16)))
                .setRefBlockBytes(ByteString.copyFrom(ByteArray.subArray(refBlockNum, 6, 8)))
                .build();
        return transaction.toBuilder().setRawData(rawData).build();
    }

    private static Sha256Sm3Hash getBlockHash(Protocol.Block block) {
        return Sha256Sm3Hash.of(block.getBlockHeader().getRawData().toByteArray());
    }

    public byte[] sign() throws InvalidProtocolBufferException {
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        Protocol.Transaction transaction = Protocol.Transaction.parseFrom(refTransaction.toByteArray());
        byte[] rawdata = transaction.getRawData().toByteArray();
        byte[] hash = Sha256Sm3Hash.hash(rawdata);
        byte[] sign = ecKey.sign(hash).toByteArray();
        return transaction.toBuilder().addSignature(ByteString.copyFrom(sign)).build().toByteArray();
    }

    public String getTransaction() {
        return ByteArray.toHexString(this.refTransaction.toByteArray());
    }
}
