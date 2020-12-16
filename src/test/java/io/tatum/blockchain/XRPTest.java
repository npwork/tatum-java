package io.tatum.blockchain;

import io.tatum.model.response.xrp.Account;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class XRPTest {
    @Test
    public void xrpGetFeeTest() throws InterruptedException, ExecutionException {
        XRP xrp = new XRP();
        BigDecimal fee = xrp.xrpGetFee();
        System.out.println(fee);
    }

    @Test
    public void xrpGetAccountInfoTest() throws InterruptedException, ExecutionException {
        XRP xrp = new XRP();
        // https://xrpscan.com/account/rQ3fNyLjbvcDaPNS4EAJY8aT9zR3uGk17c
        String address = "rQ3fNyLjbvcDaPNS4EAJY8aT9zR3uGk17c";
        Account account = xrp.xrpGetAccountInfo(address);
        System.out.println(account);
        assertThat(account, hasProperty("ledgerCurrentIndex"));
        assertThat(account, hasProperty("accountData"));
    }

    @Test
    public void xrpGetCurrentLedgerTest() throws InterruptedException, ExecutionException {
        XRP xrp = new XRP();
        BigDecimal current = xrp.xrpGetCurrentLedger();
        System.out.println(current);
    }

    @Test
    public void xrpGetLedgerTest() throws ExecutionException, InterruptedException {
        XRP xrp = new XRP();
        String ledger = xrp.xrpGetLedger(new BigDecimal(60208452));
        System.out.println(ledger);
    }

    @Test
    public void xrpGetAccountBalanceTest() throws ExecutionException, InterruptedException {
        XRP xrp = new XRP();
        // https://xrpscan.com/account/rQ3fNyLjbvcDaPNS4EAJY8aT9zR3uGk17c
        String address = "rQ3fNyLjbvcDaPNS4EAJY8aT9zR3uGk17c";
        BigDecimal balance = xrp.xrpGetAccountBalance(address);
        System.out.println(balance);

    }

    @Test
    public void xrpGetTransactionTest() throws ExecutionException, InterruptedException, IOException {
        XRP xrp = new XRP();
        // https://xrpscan.com/tx/7999663E9B35C124AA6BE4748AEDED9A66DB21BC9EF5367AC7C194BA03D84F5C
        String tx = "7999663E9B35C124AA6BE4748AEDED9A66DB21BC9EF5367AC7C194BA03D84F5C";
        String transaction = xrp.xrpGetTransaction(tx);
        System.out.println(transaction);
    }

    @Test
    public void xrpGetAccountTransactionsTest() throws ExecutionException, InterruptedException, IOException {
        XRP xrp = new XRP();
        // https://xrpscan.com/account/rQ3fNyLjbvcDaPNS4EAJY8aT9zR3uGk17c
        String address = "rQ3fNyLjbvcDaPNS4EAJY8aT9zR3uGk17c";
        BigDecimal min = BigDecimal.ZERO;
        String marker = "{\"ledger\": 60208452,\"seq\": 60208452}";
        String transaction = xrp.xrpGetAccountTransactions(address, min, marker);
        System.out.println(transaction);
    }
}
