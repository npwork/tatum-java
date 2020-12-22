package io.tatum.wallet;

import io.tatum.model.wallet.MnemonicWallet;
import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.XrpException;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.BITCOIN_MAINNET;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertNotNull;

public class WalletGeneratorTest {

    @Test
    public void generateXrpWalletTest() throws XrpException {
        XrpWallet xrpWallet = WalletGenerator.generateXrpWallet();
        assertNotNull(xrpWallet);
        System.out.println(xrpWallet.getAddress());
        System.out.println(xrpWallet.getSecret());
    }

    @Test
    public void generateBtcWalletTest() throws ExecutionException, InterruptedException {
        MnemonicWallet wallet = WalletGenerator.generateBtcWallet(BITCOIN_MAINNET,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        MatcherAssert.assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        MatcherAssert.assertThat(wallet, hasProperty("xpub", equalTo("xpub661MyMwAqRbcEZci7B2hTx1PRNfdTfWuoWyresDGq5y4SBq2KeQ7mxQMRf8EjU3oyEY85mfgkKRuWBqauUrJYcfLEPaRhHB2wvRPjsvG6DW")));
    }
}
