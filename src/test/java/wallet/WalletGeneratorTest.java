package wallet;

import io.tatum.model.wallet.XrpWallet;
import io.tatum.wallet.WalletGenerator;
import io.xpring.xrpl.XrpException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class WalletGeneratorTest {

    @Test
    public void generateXrpWalletTest() throws XrpException {
        XrpWallet xrpWallet = WalletGenerator.generateXrpWallet();
        assertNotNull(xrpWallet);
        System.out.println(xrpWallet.getAddress());
        System.out.println(xrpWallet.getSecret());
    }
}
