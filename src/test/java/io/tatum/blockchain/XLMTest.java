package io.tatum.blockchain;

import io.tatum.model.response.xlm.Account;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

public class XLMTest {
    @Test
    public void xrpGetFeeTest() throws InterruptedException, ExecutionException {
        XRP xrp = new XRP();
        BigDecimal fee = xrp.xrpGetFee();
        System.out.println(fee);
    }

    @Test
    public void xlmGetAccountInfoTest() throws InterruptedException, ExecutionException, IOException {
        XLM xlm = new XLM();
        // https://stellarchain.io/address/GC5GTUEHIU4NHKB476BL5C4NH2Z62AWQHJA3XQSQYRPOMD523OW5S2GM
        String address = "GC5GTUEHIU4NHKB476BL5C4NH2Z62AWQHJA3XQSQYRPOMD523OW5S2GM";
        Account account = xlm.xlmGetAccountInfo(address);
        System.out.println(account);
        assertThat(account, hasProperty("sequence"));
    }

    @Test
    public void xlmGetCurrentLedgerTest() throws InterruptedException, ExecutionException, IOException {
        XLM xlm = new XLM();
        String current = xlm.xlmGetCurrentLedger();
        System.out.println(current);
    }

    @Test
    public void xlmGetLedgerTest() throws ExecutionException, InterruptedException {
        XLM xlm = new XLM();
        String ledger = xlm.xlmGetLedger(new BigDecimal(33073186));
        System.out.println(ledger);
    }

    @Test
    public void xlmGetFeeTest() throws ExecutionException, InterruptedException, IOException {
        XLM xlm = new XLM();
        BigDecimal fee = xlm.xlmGetFee();
        System.out.println(fee);
    }

    @Test
    public void xlmGetLedgerTxTest() throws ExecutionException, InterruptedException, IOException {
        XLM xlm = new XLM();
        String ledger = xlm.xlmGetLedgerTx(new BigDecimal(33073186));
        System.out.println(ledger);
    }

    @Test
    public void xlmGetTransactionTest() throws ExecutionException, InterruptedException, IOException {
        XLM xlm = new XLM();
        // https://stellarchain.io/tx/2ef820fdf7dcae4c308c9642ca29023b327f6b9c5468de4cd30452df21c2b4bf
        String ledger = xlm.xlmGetTransaction("2ef820fdf7dcae4c308c9642ca29023b327f6b9c5468de4cd30452df21c2b4bf");
        System.out.println(ledger);
    }

    @Test
    public void xlmGetAccountTransactions() throws ExecutionException, InterruptedException, IOException {
        XLM xlm = new XLM();
        // https://stellarchain.io/address/GBLFABRSRFIH3LKC2ZDZBJQPC5INGW3MUUTNCZOEHUC2V4U3U4YHOZ34
        String address = "GBLFABRSRFIH3LKC2ZDZBJQPC5INGW3MUUTNCZOEHUC2V4U3U4YHOZ34";
        String transaction = xlm.xlmGetAccountTransactions(address);
        System.out.println(transaction);
    }
}
