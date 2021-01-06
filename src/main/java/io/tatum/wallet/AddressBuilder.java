package io.tatum.wallet;

import com.github.kiulian.converter.AddressConverter;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.web3j.crypto.Keys;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * The type Address builder.
 */
public class AddressBuilder {

    /**
     * The Index pub key.
     */
    DeterministicKey indexPubKey;
    /**
     * The Normal pub key.
     */
    DeterministicKey normalPubKey;
    private NetworkParameters network;

    private AddressBuilder() {
    }

    /**
     * Build address builder.
     *
     * @return the address builder
     */
    public static AddressBuilder build() {
        return new AddressBuilder();
    }

    /**
     * Network address builder.
     *
     * @param network the network
     * @return the address builder
     */
    public AddressBuilder network(NetworkParameters network) {
        this.network = network;
        return this;
    }

    /**
     * From base 58 address builder.
     *
     * @param xpub the xpub
     * @return the address builder
     */
    public AddressBuilder fromBase58(String xpub) {
        this.normalPubKey = DeterministicKey.deserializeB58(xpub, this.network);
        return this;
    }

    /**
     * Derive path address builder.
     *
     * @param path the path
     * @return the address builder
     */
    public AddressBuilder derivePath(ChildNumber path) {
        this.indexPubKey = HDKeyDerivation.deriveChildKey(this.normalPubKey, path);
        return this;
    }

    /**
     * To base 58 string.
     *
     * @return the string
     */
    public String toBase58() {
        return LegacyAddress.fromKey(this.network, ECKey.fromPublicOnly(this.indexPubKey.getPubKeyPoint())).toBase58();
    }

    /**
     * To ether address string.
     *
     * @return the string
     */
    public String toEtherAddress() {
        byte[] encoded = this.indexPubKey.getPubKeyPoint().getEncoded(false);
        BigInteger publicKey = new BigInteger(1, Arrays.copyOfRange(encoded, 1, encoded.length));
        return Keys.getAddress(publicKey);
    }

    /**
     * To cash address string.
     *
     * @return the string
     */
    public String toCashAddress() {
        return AddressConverter.toCashAddress(this.toBase58());
    }
}
