package io.tatum.transaction;

import io.tatum.model.request.transaction.FromUTXO;
import io.tatum.model.request.transaction.To;
import io.tatum.model.request.transaction.TransferBtcBasedBlockchain;
import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.BITCOIN_MAINNET;
import static io.tatum.constants.Constant.BITCOIN_TESTNET;
import static org.junit.Assert.assertEquals;

public class BitcoinTxTest {

    @Test
    public void prepareSignedTransactionTest() throws ExecutionException, InterruptedException {

        var body = new TransferBtcBasedBlockchain();
        FromUTXO[] fromUTXO = new FromUTXO[1];
        fromUTXO[0] = new FromUTXO("53faa103e8217e1520f5149a4e8c84aeb58e55bdab11164a95e69a8ca50f8fcc",
                0,
                "cVX7YtgL5muLTPncHFhP95oitV1mqUUA5VeSn8HeCRJbPqipzobf");
        body.setFromUTXO(fromUTXO);

        To[] to = new To[1];
        to[0] = new To("2MzNGwuKvMEvKMQogtgzSqJcH2UW3Tc5oc7", "0.02969944");
        body.setTo(to);

        BitcoinTx tx = new BitcoinTx();

        var txData = tx.prepareSignedTransaction(true, body);
        System.out.println(txData);
        assertEquals("02000000" + // version
                "01" + // num txIn
                "cc8f0fa58c9ae6954a1611abbd558eb5ae848c4e9a14f520157e21e803a1fa53" + // txHash
                "00000000" +
                "6a4730440220" +
                "5e49848369acc41719b669dcc9ba486c570f1ca4974f61a4321329fe35e3ff36022007485588ede47e17db992ba41aef35c72cb292f9889d471f2c592fb7f252672e0" +
                "12103b17a162956975765aa6951f6349f9ab5bf510584c5df9f6065924bfd94a08513" +
                "ffffffff" +
                "01" + // num txOut
                "58512d0000000000" +
                "17a914" +
                "4e1e4321307c88ecd4ddd6aeec040c6f01e53c99" + // to address
                "87" +
                "00000000", txData);
    }

    @Test
    public void prepareSignedTransactionTest2() throws ExecutionException, InterruptedException {

        var body = new TransferBtcBasedBlockchain();
        FromUTXO[] fromUTXO = new FromUTXO[1];
        fromUTXO[0] = new FromUTXO("53faa103e8217e1520f5149a4e8c84aeb58e55bdab11164a95e69a8ca50f8fcc",
                0,
                "cVX7YtgL5muLTPncHFhP95oitV1mqUUA5VeSn8HeCRJbPqipzobf");
        body.setFromUTXO(fromUTXO);

        To[] to = new To[1];
        to[0] = new To("2MzNGwuKvMEvKMQogtgzSqJcH2UW3Tc5oc7", "0.02969944");
        body.setTo(to);

        BitcoinTx tx = new BitcoinTx();

        var txData = tx.prepareSignedTransaction(true, body);
        assertEquals("02000000" + // version
                "01" + // num txIn
                "cc8f0fa58c9ae6954a1611abbd558eb5ae848c4e9a14f520157e21e803a1fa53" + // txHash
                "00000000" +
                "6a4730440220" +
                "5e49848369acc41719b669dcc9ba486c570f1ca4974f61a4321329fe35e3ff36022007485588ede47e17db992ba41aef35c72cb292f9889d471f2c592fb7f252672e0" +
                "12103b17a162956975765aa6951f6349f9ab5bf510584c5df9f6065924bfd94a08513" +
                "ffffffff" +
                "01" + // num txOut
                "58512d0000000000" +
                "17a914" +
                "4e1e4321307c88ecd4ddd6aeec040c6f01e53c99" + // to address
                "87" +
                "00000000", txData);
    }
}
