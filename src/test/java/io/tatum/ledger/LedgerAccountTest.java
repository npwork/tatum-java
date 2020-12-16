package io.tatum.ledger;

import io.tatum.model.request.*;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.AccountBalance;
import io.tatum.model.response.ledger.Blockage;
import io.tatum.model.response.ledger.Fiat;
import io.tatum.utils.ObjectValidator;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class LedgerAccountTest {

    public final static String TESTNET_XPUB_OF_MNEM_15 = "xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd";

    @Test
    public void createAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = createAccountWithCurrencyOnly();
        Account account = ledgerAccount.createAccount(createAccount);
        System.out.println(account);
        System.out.println(account.getBalance());
        assertThat(account, hasProperty("id", notNullValue()));

        createAccount = createAccount();
        account = ledgerAccount.createAccount(createAccount);
        System.out.println(account);
        System.out.println(account.getBalance());
        assertThat(account, hasProperty("id", notNullValue()));

        // result
        // Account(accountCode=test12345, id=5fd77feea8acfcccef97a98b, balance=AccountBalance(accountBalance=0, availableBalance=0), created=null, currency=ETH, customerId=5fd77f556f58f2797356a683, frozen=false, active=true, xpub=xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd)
        // AccountBalance(accountBalance=0, availableBalance=0)
    }

    @Test
    public void createAccountFailedTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = createAccountFailed();
        Account account = ledgerAccount.createAccount(createAccount);
        assertNull(account);
    }

    @Test
    public void createAccountNestedFailedTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = createAccountNestedFailed();
        Account account = ledgerAccount.createAccount(createAccount);
        assertNull(account);
    }

    private CreateAccount createAccountWithCurrencyOnly() {
        CreateAccount createAccount = new CreateAccount();
        createAccount.setCurrency("ETH");
        return createAccount;
    }

    private CreateAccount createAccount() {
        CreateAccount createAccount = new CreateAccount();
        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "test");
        createAccount.setCustomer(customerUpdate);

        createAccount.setAccountingCurrency(Fiat.EUR);
        createAccount.setCurrency("ETH");
        createAccount.setXpub(TESTNET_XPUB_OF_MNEM_15);
        createAccount.setAccountCode("test12345");
        return createAccount;
    }

    private CreateAccount createAccountFailed() {
        CreateAccount createAccount = new CreateAccount();
        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "test");
        createAccount.setCustomer(customerUpdate);

        createAccount.setAccountingCurrency(Fiat.EUR);
        createAccount.setCurrency("ETH");
        createAccount.setXpub("xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQdxpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd");
        createAccount.setAccountCode("test12345");
        return createAccount;
    }

    private CreateAccount createAccountNestedFailed() {
        CreateAccount createAccount = new CreateAccount();
        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "test");
        createAccount.setCustomer(customerUpdate);

        createAccount.setAccountingCurrency(Fiat.EUR);
        createAccount.setCurrency("ETH");
        createAccount.setXpub("xpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQdxpub6DYE4EtQyRirEhSujw8fCtpAHK4jxNepFiVWBy85x5Nes6vC2b2uS2dgMo22pULAHt7k79DDJnAa3DhXQnKEvc8j1s45cMC42Co7pXpnVQd");
        createAccount.setAccountCode("");
        return createAccount;
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
    public void getCreateAccountsNullFailedTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();

        CreateAccountsBatch createAccountsBatch = new CreateAccountsBatch();
//        CreateAccount[] createAccounts = new CreateAccount[2];
//        createAccountsBatch.setAccounts(createAccounts);
        Account[] accounts = ledgerAccount.createAccounts(createAccountsBatch);
        assertNull(accounts);
    }

    @Test
    public void getCreateAccountsEmptyFailedTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();

        CreateAccountsBatch createAccountsBatch = new CreateAccountsBatch();
        CreateAccount[] createAccounts = new CreateAccount[0];
        createAccountsBatch.setAccounts(createAccounts);
        Account[] accounts = ledgerAccount.createAccounts(createAccountsBatch);
        assertNull(accounts);
    }

    @Test
    public void getCreateAccountsEmptyElementFailedTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();

        CreateAccountsBatch createAccountsBatch = new CreateAccountsBatch();
        CreateAccount[] createAccounts = new CreateAccount[2];
        System.out.println(createAccounts.length);
        createAccountsBatch.setAccounts(createAccounts);
        Account[] accounts = ledgerAccount.createAccounts(createAccountsBatch);
        assertNull(accounts);
    }

    @Test
    public void getCreateAccountsNestedFailedTest() throws InterruptedException, ExecutionException, IOException {
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
        createAccount2.setCustomer(customerUpdate2);
        createAccount2.setCurrency("BTC");
        createAccount2.setAccountCode("123456789012345678901234567890123456789012345678901");

        createAccounts[1] = createAccount2;

        createAccountsBatch.setAccounts(createAccounts);

        Account[] accounts = ledgerAccount.createAccounts(createAccountsBatch);
        assertNull(accounts);
    }

    @Test
    public void blockAmountFailedTest() throws ExecutionException, InterruptedException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        String id = "5fd77feea8acfcccef97a98b";
        BlockAmount blockAmount = createBlockAmount("1234XYZ", "123ABC");
        String result = ledgerAccount.blockAmount(id, blockAmount);
        System.out.println(result);
    }

    private BlockAmount createBlockAmount(String s, String s2) {
        BlockAmount blockAmount = new BlockAmount();
        blockAmount.setAmount(s);
        blockAmount.setType(s2);
        return blockAmount;
    }

    @Test
    public void getBlockedAmountsByAccountIdTest() throws ExecutionException, InterruptedException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        String id = "5fd77feea8acfcccef97a98b";
        Blockage[] blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        System.out.println(blockages[0]);
        assertThat(blockages[0], hasProperty("accountId", notNullValue()));
        assertThat(blockages[0], hasProperty("amount", notNullValue()));
        assertThat(blockages[0], hasProperty("type", notNullValue()));
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
        BlockAmount blockAmount = createBlockAmount("9999", "999ABC");
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

    @Test
    public void deleteBlockedAmountForAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        String id = account.getId();

        // Create blocked amount
        ledgerAccount.blockAmount(id, createBlockAmount("333", "333abc"));
        ledgerAccount.blockAmount(id, createBlockAmount("444", "444abc"));

        Blockage[] blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        assertEquals(blockages.length, 2);

        ledgerAccount.deleteBlockedAmountForAccount(id);

        blockages = ledgerAccount.getBlockedAmountsByAccountId(id, null, null);
        assertEquals(blockages.length, 0);
    }

    @Test
    public void activateAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        assertNotEquals(account, null);
        String id = account.getId();

        ledgerAccount.deactivateAccount(id);
        account = ledgerAccount.getAccountById(id);
        assertEquals(account, null);

        ledgerAccount.activateAccount(id);
        account = ledgerAccount.getAccountById(id);
        assertNotEquals(account, null);
    }

    @Test
    public void deactivateAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        assertThat(account, hasProperty("active", equalTo(true)));
        String id = account.getId();
        System.out.println(id);

        ledgerAccount.getAccountById(id);
        System.out.println(id);

        ledgerAccount.deactivateAccount(id);

        account = ledgerAccount.getAccountById(id);
        assertEquals(account, null);
    }

    @Test
    public void freezeAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        assertThat(account, hasProperty("frozen", equalTo(false)));
        String id = account.getId();

        ledgerAccount.freezeAccount(id);
        account = ledgerAccount.getAccountById(id);
        assertThat(account, hasProperty("frozen", equalTo(true)));
    }

    @Test
    public void unfreezeAccountTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        assertThat(account, hasProperty("frozen", equalTo(false)));
        String id = account.getId();

        ledgerAccount.freezeAccount(id);
        account = ledgerAccount.getAccountById(id);
        assertThat(account, hasProperty("frozen", equalTo(true)));

        ledgerAccount.unfreezeAccount(id);
        account = ledgerAccount.getAccountById(id);
        assertThat(account, hasProperty("frozen", equalTo(false)));
    }

    @Test
    public void getAccountsByCustomerIdTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccount());
        String customerId = account.getCustomerId();
        System.out.println(account);
        System.out.println(customerId);

        account = ledgerAccount.createAccount(createAccount());
        customerId = account.getCustomerId();
        System.out.println(account);
        System.out.println(customerId);

        Account[] accounts = ledgerAccount.getAccountsByCustomerId(customerId, null, null);
        assertTrue(accounts.length > 0);
    }

    @Test
    public void getAllAccountsTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        String customerId = account.getCustomerId();
        System.out.println(account);
        System.out.println(customerId);

        account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());
        customerId = account.getCustomerId();
        System.out.println(account);
        System.out.println(customerId);

        Account[] accounts = ledgerAccount.getAllAccounts(null, null);
        System.out.println(accounts.length);
        assertTrue(accounts.length > 0);
    }

    @Test
    public void getAccountBalanceTest() throws InterruptedException, ExecutionException, IOException {
        LedgerAccount ledgerAccount = new LedgerAccount();
        // Create account
        Account account = ledgerAccount.createAccount(createAccountWithCurrencyOnly());

        AccountBalance accountBalance = ledgerAccount.getAccountBalance(account.getId());
        System.out.println(accountBalance);
    }
}
