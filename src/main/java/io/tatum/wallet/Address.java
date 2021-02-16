package io.tatum.wallet;

import io.tatum.model.request.Currency;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.HDUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;

/**
 * The type Address.
 */
public class Address {

    private Address() {}

    /**
     * Generate Bitcoin address
     *
     * @param testnet testnet or mainnet version of address
     * @param xpub    extended public key to generate address from
     * @param i       derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain address
     */
    private static String generateBtcAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
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
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain address
     */
    private static String generateLtcAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;
            ChildNumber path = new ChildNumber(i, false);
            return AddressBuilder.build().network(network).fromBase58(xpub).derivePath(path).toBase58();
        }).get();
    }

    /**
     * Generate Bitcoin Cash address
     *
     * @param testnet testnet or mainnet version of address
     * @param xpub    extended public key to generate address from
     * @param i       derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain address
     */
    private static String generateBchAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            org.bitcoincashj.core.NetworkParameters network = testnet ? BCH_TESTNET : BCH_MAINNET;
            org.bitcoincashj.crypto.ChildNumber path = new org.bitcoincashj.crypto.ChildNumber(i, false);
            return BchAddressBuilder.build().network(network).fromBase58(xpub).derivePath(path).toCashAddress();
        }).get();
    }

    /**
     * Generate Ethereum or any other ERC20 address
     *
     * @param testnet testnet or mainnet version of address
     * @param xpub    extended public key to generate address from
     * @param i       derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain address
     */
    private static String generateEthAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            ChildNumber path = new ChildNumber(i, false);
            NetworkParameters network = ETHEREUM_MAINNET;
            return "0x" + AddressBuilder.build().network(network).fromBase58(xpub).derivePath(path).toEtherAddress();
        }).get();
    }

    /**
     * Generate VeChain address
     *
     * @param testnet testnet or mainnet version of address
     * @param xpub    extended public key to generate address from
     * @param i       derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain address
     */
    private static String generateVetAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            ChildNumber path = new ChildNumber(i, false);
            return "0x" + AddressBuilder.build().network(VET_MAINNET).fromBase58(xpub).derivePath(path).toEtherAddress();
        }).get();
    }

    /**
     * Generate Bitcoin private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain private key to the address
     */
    private static String generateBtcPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(BTC_DERIVATION_PATH);
            return PrivateKeyBuilder.build()
                    .network(network)
                    .fromSeed(mnemonic)
                    .derivePath(path)
                    .derive(i)
                    .toWIF();
        }).get();
    }

    /**
     * Generate Litecoin private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain private key to the address
     */
    private static String generateLtcPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(LTC_DERIVATION_PATH);
            return PrivateKeyBuilder.build()
                    .network(network)
                    .fromSeed(mnemonic)
                    .derivePath(path)
                    .derive(i)
                    .toWIF();
        }).get();
    }

    /**
     * Generate Bitcoin Cash private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain private key to the address
     */
    private static String generateBchPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
            List<ChildNumber> path = HDUtils.parsePath(BCH_DERIVATION_PATH);
            return PrivateKeyBuilder.build()
                    .network(network)
                    .fromSeed(mnemonic)
                    .derivePath(path)
                    .derive(i)
                    .toWIF();
        }).get();
    }

    /**
     * Generate Ethereum or any other ERC20 private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain private key to the address
     */
    private static String generateEthPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(ETH_DERIVATION_PATH);
            return "0x" + PrivateKeyBuilder.build()
                    .fromSeed(mnemonic)
                    .derivePath(path)
                    .derive(i)
                    .toHex();
        }).get();
    }

    /**
     * Generate Ethereum or any other ERC20 private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain private key to the address
     */
    private static String generateVetPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(VET_DERIVATION_PATH);
            return "0x" + PrivateKeyBuilder.build()
                    .fromSeed(mnemonic)
                    .derivePath(path)
                    .derive(i)
                    .toHex();
        }).get();
    }

    /**
     * Generate Cardano private key from mnemonic seed
     *
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns blockchain private key to the address
     */
    private static String generateAdaPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            List<ChildNumber> path = HDUtils.parsePath(ADA_DERIVATION_PATH);
            return PrivateKeyBuilder.build()
                    .fromSeed(mnemonic)
                    .derivePath(path)
                    .derive(i)
                    .toHex();
        }).get();
    }

    /**
     * Generate address
     *
     * @param currency type of blockchain
     * @param testnet  testnet or mainnet version of address
     * @param xpub     extended public key to generate address from
     * @param i        derivation index of address to generate. Up to 2^32 addresses can be generated.
     * @return the string
     * @throws Exception the exception
     * @returns blockchain address
     */
    public static String generateAddressFromXPub(Currency currency, boolean testnet, String xpub, int i) throws Exception {
        switch (currency) {
            case BTC:
                return generateBtcAddress(testnet, xpub, i);
//            case Currency.ADA:
//                return generateAdaAddress(testnet, xpub, i);
            case LTC:
                return generateLtcAddress(testnet, xpub, i);
            case BCH:
                return generateBchAddress(testnet, xpub, i);
            case USDT:
            case LEO:
            case LINK:
            case UNI:
            case FREE:
            case MKR:
            case USDC:
            case BAT:
            case TUSD:
            case PAX:
            case PAXG:
            case PLTC:
            case XCON:
            case ETH:
            case MMY:
                return generateEthAddress(testnet, xpub, i);
            case VET:
                return generateVetAddress(testnet, xpub, i);
            default:
                throw new Exception("Unsupported blockchain.");
        }
    }

    /**
     * Generate private key from mnemonic seed
     *
     * @param currency type of blockchain
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @return the string
     * @throws Exception the exception
     * @returns blockchain private key to the address
     */
    public static String generatePrivateKeyFromMnemonic(Currency currency, Boolean testnet, String mnemonic, int i) throws Exception {
        switch (currency) {
            case BTC:
                return generateBtcPrivateKey(testnet, mnemonic, i);
            case LTC:
                return generateLtcPrivateKey(testnet, mnemonic, i);
            case BCH:
                return generateBchPrivateKey(testnet, mnemonic, i);
            case USDT:
            case LEO:
            case LINK:
            case UNI:
            case FREE:
            case MKR:
            case USDC:
            case BAT:
            case TUSD:
            case PAX:
            case PAXG:
            case PLTC:
            case XCON:
            case ETH:
            case MMY:
                return generateEthPrivateKey(testnet, mnemonic, i);
            case VET:
                return generateVetPrivateKey(testnet, mnemonic, i);
            default:
                throw new Exception("Unsupported blockchain.");
        }
    }
}
