package io.tatum.transaction.bcash;

import org.bitcoinj.core.*;
import org.bitcoinj.crypto.TransactionSignature;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.bitcoinj.core.Utils.HEX;

public class TransactionBuilder {

    private NetworkParameters network;
    private Context context;
    private Transaction transaction;

    private List<ECKey> privateKeysToSign;
    private byte[] bitcoinSerialize;
    private List<Long> amountToSign;

    private long version;
    private long lockTime;

    public TransactionBuilder(NetworkParameters network) {
        this.init(network);
    }

    private void init(NetworkParameters network) {
        this.network = network;
        this.context = new Context(network);
        this.reset();
    }

    public void reset() {
        this.transaction = new Transaction(this.network);
        this.transaction.setVersion(2);
        this.privateKeysToSign = new ArrayList<>();
        this.amountToSign = new ArrayList<>();
    }

    public void addOutput(String address, BigDecimal value) {
        Address p2SHAddress = null;//Address.fromBase58(this.network, address);
        Script scriptPubKey = ScriptBuilder.createOutputScript(p2SHAddress);
        BigDecimal satoshis = value.multiply(BigDecimal.valueOf(100000000));
        value.setScale(8, RoundingMode.FLOOR);
        Coin coin = Coin.valueOf(satoshis.longValue());
        this.transaction.addOutput(coin, scriptPubKey);
    }

    public void addInput(String txHash, long index, String key, long amount) {
        ECKey ecKey = DumpedPrivateKey.fromBase58(network, key).getKey();
        Script p2PKHOutputScript = null;//ScriptBuilder.createOutputScript(ecKey);
        byte[] message = HEX.decode(txHash);
        this.transaction.addInput(Sha256Hash.wrap(message), index, p2PKHOutputScript);
        this.privateKeysToSign.add(ecKey);
        this.amountToSign.add(amount);
    }

    private void signInput() {
        for (int i = 0; i < privateKeysToSign.size(); i++) {
            ECKey key = privateKeysToSign.get(i);
            Script scriptPubKey = null;//ScriptBuilder.createOutputScript(key);
            TransactionSignature txSignature = transaction.calculateWitnessSignature(0, key, scriptPubKey, Coin.valueOf(this.amountToSign.get(i)), Transaction.SigHash.ALL, false);
            this.transaction.getInput(i).setScriptSig(ScriptBuilder.createInputScript(txSignature, key));
        }
    }

    public TransactionBuilder build() {
        this.signInput();
        this.transaction.verify();
        this.transaction.getConfidence().setSource(TransactionConfidence.Source.SELF);
        this.transaction.setPurpose(Transaction.Purpose.USER_PAYMENT);
        this.bitcoinSerialize = this.transaction.bitcoinSerialize();
        return this;
    }

    public String toHex() {
        return HEX.encode(this.bitcoinSerialize);
    }

    public void addOutput(Coin value, Script script) {
        this.transaction.addOutput(value, script);
    }

    public void addInput(Sha256Hash txHash, long index, Script script) {
        this.transaction.addInput(txHash, index, script);
    }

    public NetworkParameters getNetwork() {
        return this.network;
    }

    public TransactionBuilder fromTransaction(Transaction transaction, String[] privateKeys) {
        // Copy transaction fields
        this.setVersion(transaction.getVersion());
        this.setLockTime(transaction.getLockTime());

        // Copy outputs (done first to avoid signature invalidation)
        transaction.getOutputs().forEach(txOut -> this.addOutput(txOut.getValue(), txOut.getScriptPubKey()));

        // Copy inputs
        transaction.getInputs().forEach(txIn -> this.addInput(txIn.getHash(), txIn.getOutpoint().getIndex(), txIn.getScriptSig()));

        for (String privKey : privateKeys) {
            ECKey ecKey = DumpedPrivateKey.fromBase58(this.network, privKey).getKey();
            this.privateKeysToSign.add(ecKey);
        }

        return this;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }
}
