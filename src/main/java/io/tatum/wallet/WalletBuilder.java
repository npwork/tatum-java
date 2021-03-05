package io.tatum.wallet;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

/**
 * The type Wallet builder.
 */
public class WalletBuilder {

    private static WalletBuilder walletBuilder;

    private DeterministicHierarchy dh;
    private DeterministicKey ehkey;
    private NetworkParameters network;

    private WalletBuilder() {
    }

    /**
     * Build wallet builder.
     *
     * @return the wallet builder
     */
    public static WalletBuilder build() {
        return new WalletBuilder();
    }

    /**
     * Network wallet builder.
     *
     * @param network the network
     * @return the wallet builder
     */
    public WalletBuilder network(NetworkParameters network) {
        this.network = network;
        return this;
    }

    /**
     * From seed wallet builder.
     *
     * @param mnemonic the mnemonic
     * @return the wallet builder
     */
    public WalletBuilder fromSeed(String mnemonic) {
        List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnemonic);
        byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        this.dh = new DeterministicHierarchy(masterPrivateKey);
        return this;
    }

    /**
     * Derive path wallet builder.
     *
     * @param path the path
     * @return the wallet builder
     */
    public WalletBuilder derivePath(List<ChildNumber> path) {
        int depth = path.size() - 1;
        this.ehkey = this.dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));
        return this;
    }

    public DeterministicKey getEhKey() {
        return this.ehkey;
    }

    /**
     * To base 58 string.
     *
     * @return the string
     */
    public String toBase58() {
        return this.ehkey.serializePubB58(this.network);
    }
}
