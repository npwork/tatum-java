package io.tatum.wallet;

import io.tatum.model.request.Currency;
import io.tatum.model.wallet.MnemonicWallet;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.WalletGenerationResult;
import io.xpring.xrpl.XrpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.wallet.DeterministicSeed;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;

public class WalletGenerator {

    /**
     * Generate Bitcoin io.tatum.wallet
     *
     * @param testnet testnet or mainnet version of address
     * @param mnem    mnemonic seed to use
     * @returns io.tatum.wallet
     */
    public static MnemonicWallet generateBtcWallet(Boolean testnet, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(BTC_DERIVATION_PATH);
            WalletBuilder walletBuilder = WalletBuilder.build().network(network).fromSeed(mnem).derivePath(path);
            return new MnemonicWallet(mnem, walletBuilder.toBase58());
        }).get();
    }

    /**
     * Generate Litecoin wallet
     *
     * @param testnet testnet or mainnet version of address
     * @param mnem    mnemonic seed to use
     * @returns wallet
     */
    public static MnemonicWallet generateLtcWallet(Boolean testnet, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(LTC_DERIVATION_PATH);
            WalletBuilder walletBuilder = WalletBuilder.build().network(network).fromSeed(mnem).derivePath(path);
            return new MnemonicWallet(mnem, walletBuilder.toBase58());
        }).get();
    }

    public static MnemonicWallet generateBchWallet(Boolean testnet, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
            List<ChildNumber> path = HDUtils.parsePath(BCH_DERIVATION_PATH);
            WalletBuilder walletBuilder = WalletBuilder.build().network(network).fromSeed(mnem).derivePath(path);
            return new MnemonicWallet(mnem, walletBuilder.toBase58());
        }).get();
    }

    /**
     * Generate VeChain wallet
     *
     * @param testnet testnet or mainnet version of address
     * @param mnem    mnemonic seed to use
     * @returns wallet
     */
    public static MnemonicWallet generateVetWallet(Boolean testnet, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = BITCOIN_MAINNET;
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(VET_DERIVATION_PATH);
            WalletBuilder walletBuilder = WalletBuilder.build().network(network).fromSeed(mnem).derivePath(path);
            return new MnemonicWallet(mnem, walletBuilder.toBase58());
        }).get();
    }

    /**
     * Generate Ethereum or any other ERC20 wallet
     *
     * @param testnet testnet or mainnet version of address
     * @param mnem    mnemonic seed to use
     * @returns wallet
     */
    public static MnemonicWallet generateEthWallet(Boolean testnet, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            NetworkParameters network = BITCOIN_MAINNET;
            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(ETH_DERIVATION_PATH);
            WalletBuilder walletBuilder = WalletBuilder.build().network(network).fromSeed(mnem).derivePath(path);
            return new MnemonicWallet(mnem, walletBuilder.toBase58());
        }).get();
    }

    /**
     * Generate Xrp address and secret.
     */
    public static MnemonicWallet generateXrpWallet() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                WalletGenerationResult generationResult = Wallet.generateRandomWallet();
                return new MnemonicWallet(generationResult.getWallet().getPrivateKey(), generationResult.getWallet().getAddress());
            } catch (XrpException e) {
                e.printStackTrace();
            }
            return null;
        }).get();
    }

    /**
     * Generate wallet
     *
     * @param currency blockchain to generate wallet for
     * @param testnet  testnet or mainnet version of address
     * @param mnemonic mnemonic seed to use. If not present, new one will be generated
     * @returns wallet or a combination of address and private key
     */
    public MnemonicWallet generateWallet(Currency currency, boolean testnet, String mnemonic) throws Exception {
        String mnem = null;
        if (StringUtils.isNotEmpty(mnemonic)) {
            mnem = mnemonic;
        } else {
            DeterministicSeed deterministicSeed = new DeterministicSeed(SecureRandom.getInstance("SHA1PRNG"), 256, Strings.EMPTY);
            deterministicSeed.getMnemonicCode();
        }
        switch (currency) {
            case BTC:
                return generateBtcWallet(testnet, mnem);
//            case Currency.ADA:
//                return generateAdaWallet(testnet, mnem);
            case LTC:
                return generateLtcWallet(testnet, mnem);
            case BCH:
                return generateBchWallet(testnet, mnem);
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
                return generateEthWallet(testnet, mnem);
            case XRP:
                return generateXrpWallet();
//            case Currency.XLM:
//                return generateXlmWallet();
            case VET:
                return generateVetWallet(testnet, mnem);
//            case Currency.NEO:
//                return generateNeoWallet();
//            case Currency.BNB:
//                return generateBnbWallet(testnet);
            default:
                throw new Exception("Unsupported blockchain.");
        }
    }
}
