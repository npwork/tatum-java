package io.tatum.ledger;

import io.tatum.model.request.Country;
import io.tatum.model.request.Currency;
import io.tatum.model.request.CustomerUpdate;
import io.tatum.model.response.common.Id;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Customer;
import io.tatum.model.response.ledger.Fiat;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerCustomerTest extends AbstractLedgerTest {
    protected final LedgerCustomer LEDGER_CUSTOMER = new LedgerCustomer();

    @Nested
    class GetByCustomerId {
        @Test
        public void notFound() throws ExecutionException, InterruptedException {
            Customer customer = LEDGER_CUSTOMER.getCustomer(faker.idNumber().valid());
            assertNull(customer);
        }

        @Test
        public void valid() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);

            verifyCustomer(account, givenCustomer);
        }
    }

    @Test
    public void getByExternalId() throws IOException, ExecutionException, InterruptedException {
        CustomerUpdate givenCustomer = getGivenCustomer();
        createAccount(Currency.ETH, givenCustomer);

        Customer customer = LEDGER_CUSTOMER.getCustomer(givenCustomer.getExternalId());
        assertEquals(toCustomerObject(givenCustomer, customer), customer);
    }

    @Test
    public void getAllCustomers() throws IOException, ExecutionException, InterruptedException {
        createAccount(Currency.ETH);

        Customer[] allCustomers = LEDGER_CUSTOMER.getAllCustomers();
        assertTrue(allCustomers.length > 0);
    }

    @Nested
    class UpdateCustomer {
        @Test
        public void notFound() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Id id = LEDGER_CUSTOMER.updateCustomer(faker.idNumber().valid(), givenCustomer);
            assertNull(id);
        }

        @Test
        public void valid() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            CustomerUpdate updatedCustomer = new CustomerUpdate(Country.HK, Fiat.AFN, Country.AD, faker.idNumber().valid());

            Account account = createAccount(Currency.ETH, givenCustomer);

            Id id = LEDGER_CUSTOMER.updateCustomer(account.getCustomerId(), updatedCustomer);
            assertEquals(account.getCustomerId(), id.getId());

            verifyCustomer(account, updatedCustomer);
        }
    }

    @Nested
    class DeactivateCustomer {
        @Test
        public void withActiveAccounts() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);
            LEDGER_CUSTOMER.deactivateCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer);
        }

        @Test
        public void withoutActiveAccounts() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);

            Arrays.stream(LEDGER_ACCOUNT.getAccountsByCustomerId(account.getCustomerId())).forEach(acc -> LEDGER_ACCOUNT.deactivateAccount(acc.getId()));

            LEDGER_CUSTOMER.deactivateCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer, false, true);
        }
    }

    @Nested
    class ActivateCustomer {
        @Test
        public void alreadyActivated() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);
            LEDGER_CUSTOMER.activateCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer);
        }

        @Test
        public void deactivated() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);

            Arrays.stream(LEDGER_ACCOUNT.getAccountsByCustomerId(account.getCustomerId())).forEach(acc -> LEDGER_ACCOUNT.deactivateAccount(acc.getId()));

            LEDGER_CUSTOMER.deactivateCustomer(account.getCustomerId());
            LEDGER_CUSTOMER.activateCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer);
        }
    }

    @Nested
    class DisableCustomer {
        @Test
        public void alreadyDisabled() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);
            LEDGER_CUSTOMER.disableCustomer(account.getCustomerId());
            LEDGER_CUSTOMER.disableCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer, true, false);
        }

        @Test
        public void enabled() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);

            LEDGER_CUSTOMER.disableCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer, true, false);
        }
    }

    @Nested
    class EnableCustomer {
        @Test
        public void alreadyEnabled() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);
            LEDGER_CUSTOMER.enableCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer);
        }

        @Test
        public void enabled() throws IOException, ExecutionException, InterruptedException {
            CustomerUpdate givenCustomer = getGivenCustomer();
            Account account = createAccount(Currency.ETH, givenCustomer);

            LEDGER_CUSTOMER.disableCustomer(account.getCustomerId());
            LEDGER_CUSTOMER.enableCustomer(account.getCustomerId());

            verifyCustomer(account, givenCustomer);
        }
    }

    private void verifyCustomer(Account account, CustomerUpdate givenCustomer) throws ExecutionException, InterruptedException {
        verifyCustomer(account, givenCustomer, true, true);
    }

    private void verifyCustomer(Account account, CustomerUpdate givenCustomer, Boolean active, Boolean enabled) throws ExecutionException, InterruptedException {
        Customer customer = LEDGER_CUSTOMER.getCustomer(account.getCustomerId());
        assertEquals(toCustomerObject(givenCustomer, customer, active, enabled), customer);
    }

    private CustomerUpdate getGivenCustomer() {
        return new CustomerUpdate(Country.SZ, Fiat.USD, Country.SZ, faker.idNumber().valid());
    }

    private Customer toCustomerObject(CustomerUpdate givenCustomer, Customer customer) {
        return toCustomerObject(givenCustomer, customer, true, true);
    }

    private Customer toCustomerObject(CustomerUpdate givenCustomer, Customer customer, Boolean active, Boolean enabled) {
        return new Customer(customer.getId(), givenCustomer.getExternalId(), givenCustomer.getCustomerCountry(), givenCustomer.getAccountingCurrency(), givenCustomer.getProviderCountry(), active, enabled);
    }
}

