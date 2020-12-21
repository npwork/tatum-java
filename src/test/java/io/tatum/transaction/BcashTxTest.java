package io.tatum.transaction;

import io.tatum.model.request.transaction.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.BITCOIN_TESTNET;
import static org.junit.Assert.assertEquals;

public class BcashTxTest {

    @Test
    public void prepareBitcoinCashSignedTransactionTest() throws ExecutionException, InterruptedException {

        var body = new TransferBchBlockchain();
        FromUTXOBcash[] fromUTXO = new FromUTXOBcash[1];
        fromUTXO[0] = new FromUTXOBcash();
        fromUTXO[0].setTxHash("53faa103e8217e1520f5149a4e8c84aeb58e55bdab11164a95e69a8ca50f8fcc");
        fromUTXO[0].setIndex(BigDecimal.valueOf(0));
        fromUTXO[0].setValue("0.0001");
        fromUTXO[0].setPrivateKey("cVX7YtgL5muLTPncHFhP95oitV1mqUUA5VeSn8HeCRJbPqipzobf");
        body.setFromUTXO(fromUTXO);

        To[] to = new To[1];
        to[0] = new To("mjJotvHmzEuyXZJGJXXknS6N3PWQnw6jf5", new BigDecimal(0.02969944));
        body.setTo(to);

        BcashTx tx = new BcashTx();

        var txData = tx.prepareBitcoinCashSignedTransaction(BITCOIN_TESTNET, body);
        assertEquals("02000000" +
                "01" +
                "cc8f0fa58c9ae6954a1611abbd558eb5ae848c4e9a14f520157e21e803a1fa53" +
                "00000000644149b200cc9f41c0247c0fe1bfa9668adbcd2edd5c04bda9446db16c34e4b6e6ed2f2c5d9db454343ce84797114779610642b03ac09cdf9754795c605ce24f4974412103b17a162956975765aa6951f6349f9ab5bf510584c5df9f6065924bfd94a08513ffffffff0158512d00000000001976a914299480256432f2372df6d66e21ed48b097797c9a88ac" +
                "00000000", txData);
    }
}
