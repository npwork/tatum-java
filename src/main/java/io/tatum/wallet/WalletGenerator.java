package io.tatum.wallet;

import io.tatum.model.wallet.MnemonicWallet;
import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.WalletGenerationResult;
import io.xpring.xrpl.XrpException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;
import static org.bitcoinj.core.Utils.HEX;
import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

public class WalletGenerator {

    /**
     * Generate Bitcoin io.tatum.wallet
     *
     * @param network testnet or mainnet version of address
     * @param mnem    mnemonic seed to use
     * @returns io.tatum.wallet
     */
    public static MnemonicWallet generateBtcWallet(Boolean network, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {

            List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnem);
            byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
            DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
            DeterministicHierarchy dh = new DeterministicHierarchy(masterPrivateKey);

            ChildNumber[] path = network ? BTC_DERIVATION_PATH : TESTNET_DERIVATION_PATH;
            NetworkParameters mainnet = network ? BITCOIN_MAINNET : BITCOIN_TESTNET;

            int depth = path.length - 1;
            DeterministicKey ehkey = dh.deriveChild(Arrays.asList(path).subList(0, depth), false, true, path[depth]);
            return new MnemonicWallet(mnem, ehkey.serializePubB58(mainnet));
        }).get();
    }

    /**
     * Generate Litecoin wallet
     * @param network testnet or mainnet version of address
     * @param mnem mnemonic seed to use
     * @returns wallet
     */
    public static MnemonicWallet generateLtcWallet(Boolean network, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {

            List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnem);
            byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
            DeterministicKey masterPrivateKey = HDKeyDerivation.createMasterPrivateKey(seed);
            DeterministicHierarchy dh = new DeterministicHierarchy(masterPrivateKey);

            ChildNumber[] path = network ? LTC_DERIVATION_PATH : TESTNET_DERIVATION_PATH;
            NetworkParameters mainnet = network ? LITECOIN_MAINNET : LITECOIN_TESTNET;

            int depth = path.length - 1;
            DeterministicKey ehkey = dh.deriveChild(Arrays.asList(path).subList(0, depth), false, true, path[depth]);
            return new MnemonicWallet(mnem, ehkey.serializePubB58(mainnet));
        }).get();
    }

    /**
     * Generate Xrp address and secret.
     */
    public static XrpWallet generateXrpWallet() throws XrpException {
        WalletGenerationResult generationResult = Wallet.generateRandomWallet();
        return new XrpWallet(generationResult.getWallet().getAddress(), generationResult.getWallet().getPrivateKey());
    }
}
