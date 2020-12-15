package io.tatum.ledger;

import io.tatum.model.request.Country;
import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Customer;
import io.tatum.model.response.ledger.Fiat;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.ledger.LedgerAccountTest.TESTNET_XPUB_OF_MNEM_15;
import static org.junit.Assert.assertEquals;

public class LedgerCustomerTest {

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
    public void getAllCustomersTest() throws InterruptedException, ExecutionException, IOException {

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

        String customerId = "5fd84ebb458e32cc52bc2f05";
        LedgerCustomer ledgerCustomer = new LedgerCustomer();
        ledgerCustomer.deactivateCustomer(customerId);

        Customer customer = ledgerCustomer.getCustomer(customerId);
        System.out.println(customer);

    }
}
