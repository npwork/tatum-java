package io.tatum.ledger.account;

import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.Currency;
import io.tatum.model.response.ledger.Account;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class LedgerCreateAccountsTest extends AbstractLedgerAccountTest {
    @Test
    public void emptyArray() throws IOException, ExecutionException, InterruptedException {
        Account[] accounts = createAccounts();
        assertEquals(0, accounts.length);
    }

    @Nested
    class OneElement {
        @Test
        public void valid() throws IOException, ExecutionException, InterruptedException {
            Account[] accounts = createAccounts(CreateAccount.builder().currency(Currency.ETH.currency).build());
            assertEquals(1, accounts.length);
            verifyAccount(Currency.ETH, accounts[0]);
        }

        @Test
        public void error() throws IOException, ExecutionException, InterruptedException {
            Account[] accounts = createAccounts(CreateAccount.builder().currency(Currency.LEO.currency).build());
            assertNull(accounts);
        }
    }

    @Nested
    class MultipleElements {
        @Test
        public void valid() throws IOException, ExecutionException, InterruptedException {
            Account[] accounts = createAccounts(
                    CreateAccount.builder().currency(Currency.ETH.currency).build(),
                    CreateAccount.builder().currency(Currency.BTC.currency).build(),
                    CreateAccount.builder().currency(Currency.XRP.currency).build()
            );
            assertEquals(3, accounts.length);
            verifyAccount(Currency.ETH, accounts[0]);
            verifyAccount(Currency.BTC, accounts[1]);
            verifyAccount(Currency.XRP, accounts[2]);
        }

        @Test
        public void error() throws IOException, ExecutionException, InterruptedException {
            Account[] accounts = createAccounts(
                    CreateAccount.builder().currency(Currency.ETH.currency).build(),
                    CreateAccount.builder().currency(Currency.LEO.currency).build(),
                    CreateAccount.builder().currency(Currency.XRP.currency).build()
            );
            assertNull(accounts);
        }
    }
}
