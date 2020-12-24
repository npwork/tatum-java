package io.tatum.wallet;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;

public class Address {

    /**
     * Generate Bitcoin address
     * @param testnet testnet or mainnet version of address
     * @param xpub extended public key to generate address from
     * @param i derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @returns blockchain address
     */
    public String generateBtcAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
            var normalPubKey = DeterministicKey.deserializeB58(xpub, network);

            var indexPubKey = HDKeyDerivation.deriveChildKey(normalPubKey, new ChildNumber(i, false));
            LegacyAddress address = LegacyAddress.fromKey(network, ECKey.fromPublicOnly(indexPubKey.getPubKeyPoint()));
            return address.toString();
        }).get();
    }

    /**
     * Generate Bitcoin address
     * @param testnet testnet or mainnet version of address
     * @param xpub extended public key to generate address from
     * @param i derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @returns blockchain address
     */
    public String generateLtcAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;
            var normalPubKey = DeterministicKey.deserializeB58(xpub, network);

            var indexPubKey = HDKeyDerivation.deriveChildKey(normalPubKey, new ChildNumber(i, false));
            LegacyAddress address = LegacyAddress.fromKey(network, ECKey.fromPublicOnly(indexPubKey.getPubKeyPoint()));
            return address.toString();
        }).get();
    }
}
