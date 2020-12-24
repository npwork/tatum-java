package io.tatum.wallet;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

public class PrivateKeyBuilder {

    private static PrivateKeyBuilder privateKeyBuilder;

    private DeterministicHierarchy dh;
    private DeterministicKey ehkey;
    private DeterministicKey indexPrivKey;
    private NetworkParameters network;

    private PrivateKeyBuilder() {
    }

    public static synchronized PrivateKeyBuilder build() {
        if (privateKeyBuilder == null) {
            privateKeyBuilder = new PrivateKeyBuilder();
        }
        return privateKeyBuilder;
    }

    public static PrivateKeyBuilder network(NetworkParameters network) {
        privateKeyBuilder.network = network;
        return privateKeyBuilder;
    }

    public static PrivateKeyBuilder fromSeed(String mnemonic) {
        List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnemonic);
        byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
        DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
        privateKeyBuilder.dh = new DeterministicHierarchy(masterPrivateKey);
        return privateKeyBuilder;
    }

    public static PrivateKeyBuilder derivePath(List<ChildNumber> path) {
        int depth = path.size() - 1;
        privateKeyBuilder.ehkey = privateKeyBuilder.dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));
        return privateKeyBuilder;
    }

    public static PrivateKeyBuilder derive(int i) {
        privateKeyBuilder.indexPrivKey = HDKeyDerivation.deriveChildKey(privateKeyBuilder.ehkey, new ChildNumber(i, false));
        return privateKeyBuilder;
    }

    public static String toWIF() {
        return privateKeyBuilder.indexPrivKey.getPrivateKeyAsWiF(privateKeyBuilder.network);
    }
}
