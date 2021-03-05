package io.tatum.wallet;

import com.google.common.collect.ImmutableList;
import io.tatum.model.request.Currency;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;
import org.tron.common.crypto.Hash;
import org.tron.common.crypto.Sha256Sm3Hash;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.core.config.Parameter;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;
import static java.util.Arrays.copyOfRange;
import static org.bitcoinj.core.ECKey.CURVE;
import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Address.
 */
public class Address {

    private Address() {
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
     * Generate Tron address
     *
     * @param xpub extended public key to generate address from
     * @param i    derivation index of address to generate. Up to 2^31 addresses can be generated.
     * @returns blockchain address
     */
    private static String generateTronAddress(Boolean testnet, String xpub, int i) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            ChildNumber child = new ChildNumber(i, false);
            ImmutableList<ChildNumber> path = ImmutableList.copyOf(HDUtils.parsePath(TRON_DERIVATION_PATH));
            int depth = path.size() - 1;
            LazyECPoint pubKey = new LazyECPoint(CURVE.getCurve(), HEX.decode(xpub.substring(0, 66)));
            DeterministicKey deterministicKey = new DeterministicKey(path.subList(0, depth),
                    HEX.decode(xpub.substring(66)), pubKey, null, depth, 0);
            DeterministicKey key = HDKeyDerivation.deriveChildKey(deterministicKey, child);
            return address2Base58(public2Address(key.getPubKeyPoint().getEncoded(false)));
        }).get();
    }

    private static String address2Base58(byte[] address) {
        byte[] hash0 = Sha256Sm3Hash.hash(address);
        byte[] hash1 = Sha256Sm3Hash.hash(hash0);
        byte[] inputCheck = new byte[address.length + 4];
        System.arraycopy(address, 0, inputCheck, 0, address.length);
        System.arraycopy(hash1, 0, inputCheck, address.length, 4);
        return Base58.encode(inputCheck);
    }

    private static byte[] public2Address(byte[] publicKey) {
        byte[] hash = Hash.sha3(copyOfRange(publicKey, 1, publicKey.length));
        System.out.println("sha3 = " + ByteArray.toHexString(hash));
        byte[] address = copyOfRange(hash, 11, hash.length);
        address[0] = Parameter.CommonConstant.ADD_PRE_FIX_BYTE_MAINNET;
        return address;
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
     * Generate Tron private key from mnemonic seed
     *
     * @param mnemonic mnemonic to generate private key from
     * @param i        derivation index of private key to generate.
     * @returns blockchain private key to the address
     */
    private static String generateTronPrivateKey(Boolean testnet, String mnemonic, int i) throws ExecutionException, InterruptedException {

        return CompletableFuture.supplyAsync(() -> {
            List<ChildNumber> path = HDUtils.parsePath(TRON_DERIVATION_PATH);
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
            case LTC:
                return generateLtcAddress(testnet, xpub, i);
            case BCH:
                return generateBchAddress(testnet, xpub, i);
            case TRON:
            case USDT_TRON:
                return generateTronAddress(testnet, xpub, i);
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
            case TRON:
            case USDT_TRON:
                return generateTronPrivateKey(testnet, mnemonic, i);
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
