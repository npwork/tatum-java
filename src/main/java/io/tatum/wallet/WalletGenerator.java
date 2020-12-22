package io.tatum.wallet;

import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.WalletGenerationResult;
import io.xpring.xrpl.XrpException;

public class WalletGenerator {

    /**
     * Generate Xrp address and secret.
     */
    public static XrpWallet generateXrpWallet() throws XrpException {
        WalletGenerationResult generationResult = Wallet.generateRandomWallet();
        return new XrpWallet(generationResult.getWallet().getAddress(), generationResult.getWallet().getPrivateKey());
    }
}
