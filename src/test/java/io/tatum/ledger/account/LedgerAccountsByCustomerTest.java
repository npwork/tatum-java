package io.tatum.ledger.account;

import io.tatum.model.request.Currency;
import io.tatum.model.response.ledger.Account;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerAccountsByCustomerTest extends AbstractLedgerAccountTest {
    @Test
    public void noAccounts() throws ExecutionException, InterruptedException {
        Account[] accounts = LEDGER_ACCOUNT.getAccountsByCustomerId(faker.idNumber().valid());
        assertNull(accounts);
    }

    @Test
    public void singleAccount() throws IOException, ExecutionException, InterruptedException {
        Account account = createAccountWithCustomer(Currency.ETH);
        Account[] accounts = LEDGER_ACCOUNT.getAccountsByCustomerId(account.getCustomerId());
        assertArrayEquals(new Account[]{account}, accounts);
    }

    @Test
    public void multipleAccounts() throws IOException, ExecutionException, InterruptedException {
        String externalId = faker.idNumber().valid();
        Account account1 = createAccountWithCustomer(Currency.ETH, externalId);
        Account account2 = createAccountWithCustomer(Currency.XRP, externalId);

        Account[] accounts = LEDGER_ACCOUNT.getAccountsByCustomerId(account1.getCustomerId());

        // @TODO - sometimes different order?
        assertEquals(new HashSet<>(List.of(account1, account2)), new HashSet<>(List.of(accounts)));
    }
}
