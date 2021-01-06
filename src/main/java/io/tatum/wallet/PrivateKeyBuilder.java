package io.tatum.wallet;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

/**
 * The type Private key builder.
 */
public class PrivateKeyBuilder {

    private DeterministicHierarchy dh;
    private DeterministicKey ehkey;
    private DeterministicKey indexPrivKey;
    private NetworkParameters network;

    private PrivateKeyBuilder() {
    }

    /**
     * Build private key builder.
     *
     * @return the private key builder
     */
    public static PrivateKeyBuilder build() {
        return new PrivateKeyBuilder();
    }

    /**
     * Network private key builder.
     *
     * @param network the network
     * @return the private key builder
     */
    public PrivateKeyBuilder network(NetworkParameters network) {
        this.network = network;
        return this;
    }

    /**
     * From seed private key builder.
     *
     * @param mnemonic the mnemonic
     * @return the private key builder
     */
    public PrivateKeyBuilder fromSeed(String mnemonic) {
        List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnemonic);
        byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        this.dh = new DeterministicHierarchy(masterPrivateKey);
        return this;
    }

    /**
     * Derive path private key builder.
     *
     * @param path the path
     * @return the private key builder
     */
    public PrivateKeyBuilder derivePath(List<ChildNumber> path) {
        int depth = path.size() - 1;
        this.ehkey = this.dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));
        return this;
    }

    /**
     * Derive private key builder.
     *
     * @param i the
     * @return the private key builder
     */
    public PrivateKeyBuilder derive(int i) {
        this.indexPrivKey = HDKeyDerivation.deriveChildKey(this.ehkey, new ChildNumber(i, false));
        return this;
    }

    /**
     * To wif string.
     *
     * @return the string
     */
    public String toWIF() {
        return this.indexPrivKey.getPrivateKeyAsWiF(this.network);
    }

    /**
     * To hex string.
     *
     * @return the string
     */
    public String toHex() {
        return this.indexPrivKey.getPrivateKeyAsHex();
    }
}
