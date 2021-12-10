package io.tatum.ledger.account;

import io.tatum.model.request.Currency;
import io.tatum.model.response.ledger.Account;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerCreateAccountTest extends AbstractLedgerAccountTest {
    @Nested
    class WithCurrencyOnly {
        @ParameterizedTest
        @ArgumentsSource(SupportedCurrenciesArgs.class)
        public void supportedCurrency(Currency currency) throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(currency);

            verifyAccount(currency, account);
            assertNull(account.getCustomerId());
            assertNull(account.getXpub());
        }

        @ParameterizedTest
        @ArgumentsSource(VirtualCurrenciesArgs.class)
        public void virtualCurrencies(Currency currency) throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(currency);
            assertNull(account);
        }
    }

    @Nested
    class WithCustomer {
        @ParameterizedTest
        @ArgumentsSource(SupportedCurrenciesArgs.class)
        public void supportedCurrency(Currency currency) throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCustomer(currency);

            verifyAccount(currency, account);
            assertNotNull(account.getCustomerId());
            assertNull(account.getXpub());
        }
    }

    @Nested
    class WithXpub {
        @ParameterizedTest
        @ArgumentsSource(SupportsXpubCurrencies.class)
        public void supports(Currency currency) throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithXPub(currency, XPUB);

            verifyAccount(currency, account);
            assertNotNull(account.getCustomerId());
            assertEquals(XPUB, account.getXpub());
        }

        @ParameterizedTest
        @ArgumentsSource(DoesNotSupportXpubCurrencies.class)
        public void doesNotSupportXpub(Currency currency) throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithXPub(currency, XPUB);
            assertNull(account);
        }

        @Nested
        class LongXpub {
            @Test
            public void notCompatibleWithEth() throws IOException, ExecutionException, InterruptedException {
                Account account = createAccountWithXPub(Currency.ETH, XPUB_WRONG);
                assertNull(account);
            }

            @Test
            public void compatibleWithXRP() throws IOException, ExecutionException, InterruptedException {
                Account account = createAccountWithXPub(Currency.XRP, XPUB_WRONG);

                verifyAccount(Currency.XRP, account);
                assertNotNull(account.getCustomerId());
                assertEquals(XPUB_WRONG, account.getXpub());
            }
        }
    }
}
