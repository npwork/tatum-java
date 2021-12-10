package io.tatum.ledger.account;

import io.tatum.model.request.Currency;
import io.tatum.model.response.ledger.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerGetAccountTest extends AbstractLedgerAccountTest {
    @ParameterizedTest
    @ArgumentsSource(SupportedCurrenciesArgs.class)
    public void withCurrency(Currency currency) throws IOException, ExecutionException, InterruptedException {
        Account created = createAccountWithCurrency(currency);

        verifyGetById(created);
    }

    @ParameterizedTest
    @ArgumentsSource(SupportedCurrenciesArgs.class)
    public void withCustomer(Currency currency) throws IOException, ExecutionException, InterruptedException {
        Account account = createAccountWithCustomer(currency);
        verifyGetById(account);
    }

    @ParameterizedTest
    @ArgumentsSource(SupportsXpubCurrencies.class)
    public void withXPub(Currency currency) throws IOException, ExecutionException, InterruptedException {
        Account created = createAccountWithXPub(currency, XPUB);

        verifyGetById(created);
    }

    @Test
    public void notFound() throws IOException, ExecutionException, InterruptedException {
        Account byId = getAccountById(faker.idNumber().valid());
        assertNull(byId);
    }

    @Test
    public void getAll() throws ExecutionException, InterruptedException, IOException {
        createAccountWithCurrency(Currency.XRP);
        Account[] allAccounts = LEDGER_ACCOUNT.getAllAccounts();

        //assertTrue(new HashSet<>(List.of(allAccounts)).contains(created));
        assertTrue(allAccounts.length != 0);
    }

    // @TODO - add https://tatum.io/apidoc#operation/getAccountBalance

    private void verifyGetById(Account expected) throws IOException, ExecutionException, InterruptedException {
        Account byId = getAccountById(expected.getId());
        assertEquals(expected, byId);
    }
}
