package io.tatum.wallet;

import io.tatum.model.wallet.MnemonicWallet;
import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.WalletGenerationResult;
import io.xpring.xrpl.XrpException;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.MnemonicCode;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.bitcoinj.core.Utils.WHITESPACE_SPLITTER;

public class WalletGenerator {

    /**
     * Generate Bitcoin io.tatum.wallet
     *
     * @param network testnet or mainnet version of address
     * @param mnem    mnemonic seed to use
     * @returns io.tatum.wallet
     */
    public static MnemonicWallet generateBtcWallet(NetworkParameters network, String mnem) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            List<String> mnemonicCode = WHITESPACE_SPLITTER.splitToList(mnem);
            byte[] seed = MnemonicCode.toSeed(mnemonicCode, "");
            DeterministicKey deterministicKey = HDKeyDerivation.createMasterPrivateKey(seed);
            return new MnemonicWallet(mnem, deterministicKey.serializePubB58(network));
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
