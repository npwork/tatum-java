package io.tatum.wallet;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;

public class Address {

    /**
     * Generate Bitcoin address
     *
     * @param testnet testnet or mainnet version of address
     * @param xpub    extended public key to generate address from
     * @param i       derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @returns blockchain address
     */
    public String generateBtcAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
            ChildNumber path = new ChildNumber(i, false);
            return AddressBuilder.build().network(network).fromBase58(xpub).derivePath(path).toBase58();
        }).get();
    }

    /**
     * Generate Bitcoin address
     *
     * @param testnet testnet or mainnet version of address
     * @param xpub    extended public key to generate address from
     * @param i       derivation index of address to generate. Up to 2^32 addresses can be generated.
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

    /**
     * Generate Bitcoin private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @returns blockchain private key to the address
     */
    public String generateBtcPrivateKey(Boolean testnet, String mnemonic, int i) {

        NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
        List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(BTC_DERIVATION_PATH);
        return PrivateKeyBuilder.build()
                .network(network)
                .fromSeed(mnemonic)
                .derivePath(path)
                .derive(i)
                .toWIF();
    }
}
