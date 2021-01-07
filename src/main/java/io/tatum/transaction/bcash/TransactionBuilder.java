package io.tatum.transaction.bcash;

import org.bitcoincashj.core.*;
import org.bitcoincashj.crypto.TransactionSignature;
import org.bitcoincashj.script.Script;
import org.bitcoincashj.script.ScriptBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Transaction builder.
 */
public class TransactionBuilder {

    private NetworkParameters network;
    private Context context;
    private Transaction transaction;

    private List<ECKey> privateKeysToSign;
    private byte[] bitcoinSerialize;
    private List<Long> amountToSign;

    private long version;
    private long lockTime;

    /**
     * Instantiates a new Transaction builder.
     *
     * @param network the network
     */
    public TransactionBuilder(NetworkParameters network) {
        this.init(network);
    }

    private void init(NetworkParameters network) {
        this.network = network;
        this.context = new Context(network);
        this.reset();
    }

    /**
     * Reset.
     */
    public void reset() {
        this.transaction = new Transaction(this.network);
        this.transaction.setVersion(2);
        this.privateKeysToSign = new ArrayList<>();
        this.amountToSign = new ArrayList<>();
    }

    /**
     * Add output.
     *
     * @param address the address
     * @param value   the value
     */
    public void addOutput(String address, BigDecimal value) {
        Address p2SHAddress = Address.fromBase58(this.network, address);
        Script scriptPubKey = ScriptBuilder.createOutputScript(p2SHAddress);
        BigDecimal satoshis = value.multiply(BigDecimal.valueOf(100000000)).setScale(8, RoundingMode.FLOOR);
        Coin coin = Coin.valueOf(satoshis.longValue());
        this.transaction.addOutput(coin, scriptPubKey);
    }

    /**
     * Add input.
     *
     * @param txHash the tx hash
     * @param index  the index
     * @param key    the key
     * @param amount the amount
     */
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

    /**
     * Build transaction builder.
     *
     * @return the transaction builder
     */
    public TransactionBuilder build() {
        this.signInput();
        this.transaction.verify();
        this.transaction.getConfidence().setSource(TransactionConfidence.Source.SELF);
        this.transaction.setPurpose(Transaction.Purpose.USER_PAYMENT);
        this.bitcoinSerialize = this.transaction.bitcoinSerialize();
        return this;
    }

    /**
     * To hex string.
     *
     * @return the string
     */
    public String toHex() {
        return HEX.encode(this.bitcoinSerialize);
    }

    /**
     * Add output.
     *
     * @param value  the value
     * @param script the script
     */
    public void addOutput(Coin value, Script script) {
        this.transaction.addOutput(value, script);
    }

    /**
     * Add input.
     *
     * @param txHash the tx hash
     * @param index  the index
     * @param script the script
     */
    public void addInput(Sha256Hash txHash, long index, Script script) {
        this.transaction.addInput(txHash, index, script);
    }

    /**
     * Gets network.
     *
     * @return the network
     */
    public NetworkParameters getNetwork() {
        return this.network;
    }

    /**
     * From transaction transaction builder.
     *
     * @param transaction the transaction
     * @param privateKeys the private keys
     * @return the transaction builder
     */
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

    /**
     * Gets version.
     *
     * @return the version
     */
    public long getVersion() {
        return version;
    }

    /**
     * Sets version.
     *
     * @param version the version
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Gets lock time.
     *
     * @return the lock time
     */
    public long getLockTime() {
        return lockTime;
    }

    /**
     * Sets lock time.
     *
     * @param lockTime the lock time
     */
    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }
}
