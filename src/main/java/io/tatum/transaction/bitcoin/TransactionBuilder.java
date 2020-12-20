package io.tatum.transaction.bitcoin;

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

    private static TransactionBuilder instance;

    private NetworkParameters network;
    private Context context;
    private Transaction transaction;

    private List<ECKey> privateKeysToSign;
    private byte[] bitcoinSerialize;

    private TransactionBuilder() {
    }

    public static synchronized TransactionBuilder getInstance() {
        if (instance == null) {
            instance = new TransactionBuilder();
        }
        return instance;
    }

    public TransactionBuilder Init(NetworkParameters network) {
        this.network = network;
        this.context = new Context(network);
        this.transaction = new Transaction(network);
        this.transaction.setVersion(2);
        this.privateKeysToSign = new ArrayList<>();
        return this;
    }

    public TransactionBuilder addOutput(String address, BigDecimal value) {
        Address p2SHAddress = LegacyAddress.fromBase58(this.network, address);
        Script scriptPubKey = ScriptBuilder.createOutputScript(p2SHAddress);
        BigDecimal satoshis = value.multiply(BigDecimal.valueOf(100000000));
        value.setScale(8, RoundingMode.FLOOR);
        Coin coin = Coin.valueOf(satoshis.longValue());
        this.transaction.addOutput(coin, scriptPubKey);
        return this;
    }

    public TransactionBuilder addInput(String txHash, long index, String key) {
        ECKey ecKey = DumpedPrivateKey.fromBase58(network, key).getKey();
        Script p2PKHOutputScript = ScriptBuilder.createP2PKHOutputScript(ecKey);
        byte[] message = HEX.decode(txHash);
        this.transaction.addInput(Sha256Hash.wrap(message), index, p2PKHOutputScript);
        this.privateKeysToSign.add(ecKey);
        return this;
    }

    private TransactionBuilder signInput() {
        for (int i = 0; i < privateKeysToSign.size(); i++) {
            ECKey key = privateKeysToSign.get(i);
            Address sourceAddress = Address.fromKey(this.network, key, Script.ScriptType.P2PKH);
            Script scriptPubKey = ScriptBuilder.createOutputScript(sourceAddress);
            TransactionSignature txSignature = transaction.calculateSignature(0, key, scriptPubKey, Transaction.SigHash.ALL, false);
            this.transaction.getInput(i).setScriptSig(ScriptBuilder.createInputScript(txSignature, key));
        }
        return this;
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

    public NetworkParameters getNetwork() {
        return this.network;
    }
}
