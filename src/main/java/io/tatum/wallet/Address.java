package io.tatum.wallet;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;

public class Address {

    /**
     * Generate Bitcoin address
     * @param network testnet or mainnet version of address
     * @param xpub extended public key to generate address from
     * @param i derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @returns blockchain address
     */
    public String generateBtcAddress(NetworkParameters network, String xpub, int i) {
        var normalPubKey = DeterministicKey.deserializeB58(xpub, network);
        normalPubKey = HDKeyDerivation.deriveChildKey(normalPubKey, new ChildNumber(i, false));
        LegacyAddress address = LegacyAddress.fromKey(network, ECKey.fromPublicOnly(normalPubKey.getPubKeyPoint()));
        return address.toString();
    }

    /**
     * Generate Bitcoin address
     * @param network testnet or mainnet version of address
     * @param xpub extended public key to generate address from
     * @param i derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @returns blockchain address
     */
    public String generateLtcAddress(NetworkParameters network, String xpub, int i) {
        var normalPubKey = DeterministicKey.deserializeB58(xpub, network);
        normalPubKey = HDKeyDerivation.deriveChildKey(normalPubKey, new ChildNumber(i, false));
        LegacyAddress address = LegacyAddress.fromKey(network, ECKey.fromPublicOnly(normalPubKey.getPubKeyPoint()));
        return address.toString();
    }
}
