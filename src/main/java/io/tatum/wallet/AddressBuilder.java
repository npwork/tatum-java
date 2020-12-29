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

public class AddressBuilder {

    DeterministicKey indexPubKey;
    DeterministicKey normalPubKey;
    private NetworkParameters network;

    private AddressBuilder() {
    }

    public static AddressBuilder build() {
        return new AddressBuilder();
    }

    public AddressBuilder network(NetworkParameters network) {
        this.network = network;
        return this;
    }

    public AddressBuilder fromBase58(String xpub) {
        this.normalPubKey = DeterministicKey.deserializeB58(xpub, this.network);
        return this;
    }

    public AddressBuilder derivePath(ChildNumber path) {
        this.indexPubKey = HDKeyDerivation.deriveChildKey(this.normalPubKey, path);
        return this;
    }

    public String toBase58() {
        return LegacyAddress.fromKey(this.network, ECKey.fromPublicOnly(this.indexPubKey.getPubKeyPoint())).toBase58();
    }

    public String toEtherAddress() {
        byte[] encoded = this.indexPubKey.getPubKeyPoint().getEncoded(false);
        BigInteger publicKey = new BigInteger(1, Arrays.copyOfRange(encoded, 1, encoded.length));
        return Keys.getAddress(publicKey);
    }

    public String toCashAddress() {
        return AddressConverter.toCashAddress(this.toBase58());
    }
}
