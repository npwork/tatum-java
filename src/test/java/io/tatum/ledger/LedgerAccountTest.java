package io.tatum.ledger;

import io.tatum.model.request.*;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Blockage;
import io.tatum.model.response.ledger.Fiat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.in;
import static org.junit.Assert.assertEquals;

public class LedgerAccountTest {

    public final static String TESTNET_XPUB_OF_MNEM_15 = "xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd";

    @Test
    public void getCreateAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = new CreateAccount();

        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "test");
        createAccount.setCustomer(customerUpdate);

        createAccount.setCurrency("ETH");
        createAccount.setXpub(TESTNET_XPUB_OF_MNEM_15);
        createAccount.setAccountCode("test12345");

        Account account = ledgerAccount.createAccount(createAccount);
        System.out.println(account);
        System.out.println(account.getBalance());
        assertThat(account, hasProperty("id", notNullValue()));
        // result
        // Account(accountCode=test12345, id=5fd77feea8acfcccef97a98b, balance=AccountBalance(accountBalance=0, availableBalance=0), created=null, currency=ETH, customerId=5fd77f556f58f2797356a683, frozen=false, active=true, xpub=xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd)
        // AccountBalance(accountBalance=0, availableBalance=0)
    }

    @Test
    public void getAccountByIdTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        String id = "5fd77feea8acfcccef97a98b";
        Account account = ledgerAccount.getAccountById(id);
        System.out.println(account);
        assertThat(account, hasProperty("currency"));
        assertThat(account, hasProperty("xpub"));
    }

    @Test
    public void getCreateAccountsTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();

        CreateAccountsBatch createAccountsBatch = new CreateAccountsBatch();
        CreateAccount[] createAccounts = new CreateAccount[2];

        CreateAccount createAccount = new CreateAccount();
        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "testA");
        createAccount.setCustomer(customerUpdate);
        createAccount.setCurrency("ETH");
        createAccount.setXpub(TESTNET_XPUB_OF_MNEM_15);
        createAccount.setAccountCode("test12345");

        createAccounts[0] = createAccount;

        CreateAccount createAccount2 = new CreateAccount();
        CustomerUpdate customerUpdate2 = new CustomerUpdate(Country.US, Fiat.USD, Country.SZ, "testB");
        createAccount2.setCustomer(customerUpdate);
        createAccount2.setCurrency("BTC");
        createAccount2.setAccountCode("test6789");

        createAccounts[1] = createAccount2;

        createAccountsBatch.setAccounts(createAccounts);

        Account[] accounts = ledgerAccount.createAccounts(createAccountsBatch);
        System.out.println(accounts[1]);
        assertThat(accounts[0], hasProperty("id", notNullValue()));
        assertThat(accounts[0], hasProperty("currency", equalTo("ETH")));

        assertThat(accounts[1], hasProperty("id", notNullValue()));
        assertThat(accounts[1], hasProperty("currency", equalTo("BTC")));
    }

    @Test
    public void blockAmountTest() throws ExecutionException, InterruptedException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        String id = "5fd77feea8acfcccef97a98b";
        BlockAmount blockAmount = new BlockAmount();
        blockAmount.setAmount("1234");
        blockAmount.setType("123ABC");
        String result = ledgerAccount.blockAmount(id, blockAmount);
        System.out.println(result);
    }

    @Test
    public void getBlockedAmountsByAccountIdTest() throws ExecutionException, InterruptedException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        String id = "5fd77feea8acfcccef97a98b";
        Blockage[] blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        System.out.println(blockages[0]);
        assertThat(blockages[0], hasProperty("accountId", notNullValue()));
        assertThat(blockages[0], hasProperty("amount", equalTo("1234")));
        assertThat(blockages[0], hasProperty("type", equalTo("123ABC")));
        assertThat(blockages[0], hasProperty("accountId", equalTo(id)));
    }

    @Test
    public void deleteBlockedAmountTest() throws InterruptedException, ExecutionException, IOException {

        LedgerAccount ledgerAccount = new LedgerAccount();

        String id = "5fd77feea8acfcccef97a98b";
        Blockage[] blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        System.out.println(blockages.length);
        int index = blockages.length;

        // create new block amount
        BlockAmount blockAmount = new BlockAmount();
        blockAmount.setAmount("9999");
        blockAmount.setType("999ABC");
        String result = ledgerAccount.blockAmount(id, blockAmount);
        System.out.println(result);

        blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        System.out.println(blockages.length);
        assertEquals(blockages.length, index + 1);
        System.out.println(blockages[blockages.length - 1]);

        // delete
        ledgerAccount.deleteBlockedAmount(blockages[blockages.length - 1].getId());

        blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        System.out.println(blockages.length);
        assertEquals(blockages.length, index);
    }
}
