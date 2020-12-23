package io.tatum.wallet;

import io.tatum.model.wallet.MnemonicWallet;
import io.tatum.model.wallet.XrpWallet;
import io.xpring.xrpl.XrpException;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
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
        MnemonicWallet wallet = WalletGenerator.generateBtcWallet(true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("xpub6DtevPy3xnhGgA8J5cveZcotW85Zs5gzGvTBLXBTkJrMGfB9QeM7htonAz43ZHS9McEXLdfeew2V8TUjskuab5kxE25rVsjMrWmZzmB2Fnp")));
    }

    @Test
    public void generateLtcWalletTest() throws ExecutionException, InterruptedException {
        MnemonicWallet wallet = WalletGenerator.generateLtcWallet(true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        assertThat(wallet, hasProperty("mnemonic", equalTo("quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten")));
        assertThat(wallet, hasProperty("xpub", equalTo("Ltub2aXe9g8ZjLhaiqshH334GiqSGFe2bgmey7jssB9WmCBW4TDVoAN7y5iyZBK9zCoXBMiBnYbU774DBQ9yXB6QpFWCCJmh5mJ6Mq6PqdTV7WG")));
    }
}
