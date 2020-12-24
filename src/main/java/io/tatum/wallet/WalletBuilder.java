package io.tatum.wallet;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

public class WalletBuilder {

    private static WalletBuilder walletBuilder;

    private DeterministicHierarchy dh;
    private DeterministicKey ehkey;
    private NetworkParameters network;

    private WalletBuilder() {
    }

    public static WalletBuilder build() {
        walletBuilder = new WalletBuilder();
        return walletBuilder;
    }

    public static WalletBuilder network(NetworkParameters network) {
        walletBuilder.network = network;
        return walletBuilder;
    }

    public static WalletBuilder fromSeed(String mnemonic) {
        List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnemonic);
        byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        walletBuilder.dh = new DeterministicHierarchy(masterPrivateKey);
        return walletBuilder;
    }

    public static WalletBuilder derivePath(List<ChildNumber> path) {
        int depth = path.size() - 1;
        walletBuilder.ehkey = walletBuilder.dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));
        return walletBuilder;
    }

    public static String toBase58() {
        return walletBuilder.ehkey.serializePubB58(walletBuilder.network);
    }
}
