package io.tatum.wallet;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

public class PrivateKeyBuilder {

    private DeterministicHierarchy dh;
    private DeterministicKey ehkey;
    private DeterministicKey indexPrivKey;
    private NetworkParameters network;

    private PrivateKeyBuilder() {
    }

    public static PrivateKeyBuilder build() {
        return new PrivateKeyBuilder();
    }

    public PrivateKeyBuilder network(NetworkParameters network) {
        this.network = network;
        return this;
    }

    public PrivateKeyBuilder fromSeed(String mnemonic) {
        List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnemonic);
        byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        this.dh = new DeterministicHierarchy(masterPrivateKey);
        return this;
    }

    public PrivateKeyBuilder derivePath(List<ChildNumber> path) {
        int depth = path.size() - 1;
        this.ehkey = this.dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));
        return this;
    }

    public PrivateKeyBuilder derive(int i) {
        this.indexPrivKey = HDKeyDerivation.deriveChildKey(this.ehkey, new ChildNumber(i, false));
        return this;
    }

    public String toWIF() {
        return this.indexPrivKey.getPrivateKeyAsWiF(this.network);
    }

    public String toHex() {
        return this.indexPrivKey.getPrivateKeyAsHex();
    }
}
