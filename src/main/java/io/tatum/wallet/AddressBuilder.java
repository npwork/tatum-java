package io.tatum.wallet;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

public class AddressBuilder {

    private static AddressBuilder addressBuilder;

    DeterministicKey indexPubKey;
    DeterministicKey normalPubKey;
    private NetworkParameters network;

    private AddressBuilder() {
    }

    public static synchronized AddressBuilder build() {
        if (addressBuilder == null) {
            addressBuilder = new AddressBuilder();
        }
        return addressBuilder;
    }

    public static AddressBuilder network(NetworkParameters network) {
        addressBuilder.network = network;
        return addressBuilder;
    }

    public static AddressBuilder fromBase58(String xpub) {
        addressBuilder.normalPubKey = DeterministicKey.deserializeB58(xpub, addressBuilder.network);
        return addressBuilder;
    }

    public static AddressBuilder derivePath(ChildNumber path) {
        addressBuilder.indexPubKey = HDKeyDerivation.deriveChildKey(addressBuilder.normalPubKey, path);
        return addressBuilder;
    }

    public static String toBase58() {
        return LegacyAddress.fromKey(addressBuilder.network, ECKey.fromPublicOnly(addressBuilder.indexPubKey.getPubKeyPoint())).toBase58();
    }
}
