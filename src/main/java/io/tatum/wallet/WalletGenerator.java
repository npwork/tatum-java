package io.tatum.wallet;

import io.tatum.model.wallet.MnemonicWallet;
import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.WalletGenerationResult;
import io.xpring.xrpl.XrpException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;
import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

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

            List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnem);
            byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
            DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
            DeterministicHierarchy dh = new DeterministicHierarchy(masterPrivateKey);

            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(BTC_DERIVATION_PATH);
            NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;

            int depth = path.size() - 1;
            DeterministicKey ehkey = dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));
            return new MnemonicWallet(mnem, ehkey.serializePubB58(network));
        }).get();
    }

    /**
     * Generate Litecoin wallet
     * @param testnet testnet or mainnet version of address
     * @param mnem mnemonic seed to use
     * @returns wallet
     */
    public static MnemonicWallet generateLtcWallet(Boolean testnet, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {

            List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnem);
            byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
            DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
            DeterministicHierarchy dh = new DeterministicHierarchy(masterPrivateKey);

            List<ChildNumber> path = testnet ? HDUtils.parsePath(TESTNET_DERIVATION_PATH) : HDUtils.parsePath(LTC_DERIVATION_PATH);
            NetworkParameters network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;

            int depth = path.size() - 1;
            DeterministicKey ehkey = dh.deriveChild(path.subList(0, depth), false, true, path.get(depth));

            return new MnemonicWallet(mnem, ehkey.serializePubB58(network));
        }).get();
    }

    /**
     * Generate Xrp address and secret.
     */
    public static XrpWallet generateXrpWallet() throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            WalletGenerationResult generationResult = null;
            try {
                generationResult = Wallet.generateRandomWallet();
                return new XrpWallet(generationResult.getWallet().getAddress(), generationResult.getWallet().getPrivateKey());
            } catch (XrpException e) {
                e.printStackTrace();
            }
            return null;
        }).get();
    }
}
