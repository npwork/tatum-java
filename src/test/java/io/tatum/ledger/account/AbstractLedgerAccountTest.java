package io.tatum.ledger.account;

import com.github.javafaker.Faker;
import io.tatum.ledger.AbstractLedgerTest;
import io.tatum.ledger.LedgerAccount;
import io.tatum.model.request.*;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.AccountBalance;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractLedgerAccountTest extends AbstractLedgerTest {
    public static class SupportedCurrenciesArgs implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Currency.supportedCurrencies().stream().map(Arguments::of);
        }
    }

    public static class VirtualCurrenciesArgs implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Currency.virtualCurrencies().stream().map(Arguments::of);
        }
    }

    static class SupportsXpubCurrencies implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Currency.supportsXpub().stream().map(Arguments::of);
        }
    }

    static class DoesNotSupportXpubCurrencies implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Currency.doesNotSupportXpub().stream().map(Arguments::of);
        }
    }

    protected Account getAccountById(String id) throws IOException, ExecutionException, InterruptedException {
        return LEDGER_ACCOUNT.getAccountById(id);
    }

    protected Account[] createAccounts(CreateAccount... accounts) throws IOException, ExecutionException, InterruptedException {
        return LEDGER_ACCOUNT.createAccounts(new CreateAccountsBatch(List.of(accounts)));
    }

    protected void verifyAccount(Currency currency, Account account) {
        assertEquals(currency.currency, account.getCurrency());
        assertNotNull(account.getId());
        assertTrue(account.getActive());
        assertEquals(new AccountBalance(BigDecimal.ZERO, BigDecimal.ZERO), account.getBalance());
    }
}
