package io.tatum.wallet;

import org.bitcoincashj.core.Address;
import org.bitcoincashj.core.CashAddressFactory;
import org.bitcoincashj.core.ECKey;
import org.bitcoincashj.core.NetworkParameters;
import org.bitcoincashj.crypto.ChildNumber;
import org.bitcoincashj.crypto.DeterministicKey;
import org.bitcoincashj.crypto.HDKeyDerivation;

/**
 * The type Address builder.
 */
public class BchAddressBuilder {

    /**
     * The Index pub key.
     */
    DeterministicKey indexPubKey;
    /**
     * The Normal pub key.
     */
    DeterministicKey normalPubKey;
    private NetworkParameters network;

    private BchAddressBuilder() {
    }

    /**
     * Build address builder.
     *
     * @return the address builder
     */
    public static BchAddressBuilder build() {
        return new BchAddressBuilder();
    }

    /**
     * Network address builder.
     *
     * @param network the network
     * @return the address builder
     */
    public BchAddressBuilder network(NetworkParameters network) {
        this.network = network;
        return this;
    }

    /**
     * From base 58 address builder.
     *
     * @param xpub the xpub
     * @return the address builder
     */
    public BchAddressBuilder fromBase58(String xpub) {
        this.normalPubKey = DeterministicKey.deserializeB58(xpub, this.network);
        return this;
    }

    /**
     * Derive path address builder.
     *
     * @param path the path
     * @return the address builder
     */
    public BchAddressBuilder derivePath(ChildNumber path) {
        this.indexPubKey = HDKeyDerivation.deriveChildKey(this.normalPubKey, path);
        return this;
    }

    /**
     * To base 58 string.
     *
     * @return the string
     */
    public String toBase58() {
        return Address.fromKey(this.network, ECKey.fromPublicOnly(this.indexPubKey.getPubKeyPoint(), true)).toBase58();
    }

    /**
     * To cash address string.
     *
     * @return the string
     */
    public String toCashAddress() {
        return CashAddressFactory.create().getFromBase58(this.network, this.toBase58()).toString();
    }
}
