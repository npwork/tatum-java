package io.tatum.ledger;

import io.tatum.model.request.Country;
import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Customer;
import io.tatum.model.response.ledger.Fiat;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.ledger.LedgerAccountTest.TESTNET_XPUB_OF_MNEM_15;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;

public class LedgerCustomerTest {

    public final static String MAINNET_XPUB_OF_MNEM_15 = "xpub6ESrwNgjAnJvP8yyuUrxj8NbthXVbifd6QnztoRPCdAzTFmu6wWGq4sWG3GrYAouuWc2EejAVCAPbm5hyLxdHVcoGQqYV3CNU5s3bR1ZnkG";

    @Test
    public void getCustomerTest() throws ExecutionException, InterruptedException, IOException {

        LedgerAccount ledgerAccount = new LedgerAccount();
        CreateAccount createAccount = createAccount();

        Account account = ledgerAccount.createAccount(createAccount);

        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        Customer customer1 = ledgerCustomer.getCustomer(account.getCustomerId());
        System.out.println(customer1);

        Customer customer2 = ledgerCustomer.getCustomer(customer1.getExternalId());
        System.out.println(customer2);

        assertEquals(customer1, customer2);
    }

    private CreateAccount createAccount() {
        CreateAccount createAccount = new CreateAccount();

        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, "externalId123");
        createAccount.setCustomer(customerUpdate);

        createAccount.setCurrency("ETH");
        createAccount.setXpub(TESTNET_XPUB_OF_MNEM_15);
        createAccount.setAccountCode("test12345");
        return createAccount;
    }

    @Test
    public void getAllCustomersTest() throws InterruptedException, ExecutionException {

        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        Customer[] customers = ledgerCustomer.getAllCustomers(null, null);
        System.out.println(customers.length);
        System.out.println(customers[customers.length - 1]);
    }

    @Test
    public void updateCustomerTest() throws InterruptedException, ExecutionException, IOException {

        String customerId = "5fd84ebb458e32cc52bc2f05";
        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        Customer customer1 = ledgerCustomer.getCustomer(customerId);
        System.out.println(customer1);

        CustomerUpdate customerUpdate = new CustomerUpdate(Country.FR, Fiat.USD, Country.CA, "externalId123");
        Id id = ledgerCustomer.updateCustomer(customerId, customerUpdate);
        System.out.println(id);
    }

    @Test
    public void deactivateCustomerTest() throws InterruptedException, ExecutionException, IOException {

        LedgerAccount ledgerAccount = new LedgerAccount();

        String externalId = RandomStringUtils.randomAlphabetic(24);
        Account account = ledgerAccount.createAccount(createAccount3(externalId));

        String customerId = account.getCustomerId();
        Account[] accounts = ledgerAccount.getAccountsByCustomerId(customerId, null, null);
        System.out.println(accounts.length);

        for (Account acc : accounts) {
            System.out.println(acc.getId());
            ledgerAccount.deactivateAccount(acc.getId());
        }

        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        ledgerCustomer.deactivateCustomer(customerId);

        Customer customer = ledgerCustomer.getCustomer(customerId);
        System.out.println(customer);
        assertThat(customer, hasProperty("active", equalTo(false)));

        // customerId=5fd85a946f306d7998941278
        // id=5fd85b9a12e8e94a9dd8310c
        // externalId=externalId123456789123456789123456789
    }

    private CreateAccount createAccount3(String externalId) {
        CreateAccount createAccount = new CreateAccount();

        CustomerUpdate customerUpdate = new CustomerUpdate(Country.SZ, Fiat.EUR, Country.SZ, externalId);
        createAccount.setCustomer(customerUpdate);

        createAccount.setCurrency("ETH");
        createAccount.setXpub(MAINNET_XPUB_OF_MNEM_15);
        createAccount.setAccountCode("test6789");
        return createAccount;
    }

    @Test
    public void activateCustomerTest() throws InterruptedException, ExecutionException, IOException {

        LedgerAccount ledgerAccount = new LedgerAccount();
        String externalId = RandomStringUtils.randomAlphabetic(24);
        Account account = ledgerAccount.createAccount(createAccount3(externalId));

        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        String customerId = account.getCustomerId();

        Customer customer = ledgerCustomer.getCustomer(customerId);
        System.out.println(customer);
        assertThat(customer, hasProperty("active", equalTo(true)));

        // reactivating
        Account[] accounts = ledgerAccount.getAccountsByCustomerId(customerId, null, null);
        System.out.println(accounts.length);

        for (Account acc : accounts) {
            System.out.println(acc.getId());
            ledgerAccount.deactivateAccount(acc.getId());
        }

        ledgerCustomer.deactivateCustomer(customerId);

        customer = ledgerCustomer.getCustomer(customerId);
        System.out.println(customer);
        assertThat(customer, hasProperty("active", equalTo(false)));

        // activating
        ledgerCustomer = new LedgerCustomer();
        ledgerCustomer.activateCustomer(customerId);

        customer = ledgerCustomer.getCustomer(customerId);
        System.out.println(customer);
        assertThat(customer, hasProperty("active", equalTo(true)));

    }

    @Test
    public void disableCustomerTest() throws InterruptedException, ExecutionException, IOException {

        LedgerAccount ledgerAccount = new LedgerAccount();
        String externalId = RandomStringUtils.randomAlphabetic(24);
        Account account = ledgerAccount.createAccount(createAccount3(externalId));

        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        ledgerCustomer.disableCustomer(account.getCustomerId());

        Customer customer = ledgerCustomer.getCustomer(account.getCustomerId());
        assertThat(customer, hasProperty("enabled", equalTo(false)));
    }

    @Test
    public void enableCustomerTest() throws InterruptedException, ExecutionException, IOException {

        LedgerAccount ledgerAccount = new LedgerAccount();
        String externalId = RandomStringUtils.randomAlphabetic(24);
        Account account = ledgerAccount.createAccount(createAccount3(externalId));

        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        ledgerCustomer.disableCustomer(account.getCustomerId());

        Customer customer = ledgerCustomer.getCustomer(account.getCustomerId());
        assertThat(customer, hasProperty("enabled", equalTo(false)));

        ledgerCustomer.enableCustomer(account.getCustomerId());
        customer = ledgerCustomer.getCustomer(account.getCustomerId());
        assertThat(customer, hasProperty("enabled", equalTo(true)));
    }
}

