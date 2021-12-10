package io.tatum.ledger.account;

import io.tatum.model.request.Currency;
import io.tatum.model.response.ledger.Account;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerAccountActiveFrozenTest extends AbstractLedgerAccountTest {
    @Nested
    class Deactivate {
        @Test
        public void deactivateActive() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.deactivateAccount(account.getId());

            verifyDeactivated(account);
        }

        @Test
        public void deactivateAlreadyDeactived() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.deactivateAccount(account.getId());
            LEDGER_ACCOUNT.deactivateAccount(account.getId());

            verifyDeactivated(account);
        }

        private void verifyDeactivated(Account account) throws IOException, ExecutionException, InterruptedException {
            Account accountById = getAccountById(account.getId());
            assertNull(accountById);
        }
    }

    @Nested
    class Activate {
        @Test
        public void alreadyActivated() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.activateAccount(account.getId());

            verifyActivated(account);
        }

        @Test
        public void activateDeactivated() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.deactivateAccount(account.getId());
            LEDGER_ACCOUNT.activateAccount(account.getId());

            verifyActivated(account);
        }

        private void verifyActivated(Account account) throws IOException, ExecutionException, InterruptedException {
            Account accountById = getAccountById(account.getId());
            verifyAccount(Currency.ETH, accountById);
        }
    }

    @Nested
    class Freeze {
        @Test
        public void freeze() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.freezeAccount(account.getId());

            verifyFreeze(account);
        }

        @Test
        public void alreadyFrozen() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.freezeAccount(account.getId());
            LEDGER_ACCOUNT.freezeAccount(account.getId());

            verifyFreeze(account);
        }

        private void verifyFreeze(Account account) throws IOException, ExecutionException, InterruptedException {
            Account accountById = getAccountById(account.getId());

            assertTrue(accountById.getFrozen());
            verifyAccount(Currency.ETH, accountById);
        }
    }

    @Nested
    class Unfreeze {
        @Test
        public void unfreezeNotFrozen() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            verifyUnfreeze(account);
        }

        @Test
        public void unfreeze() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            LEDGER_ACCOUNT.freezeAccount(account.getId());
            LEDGER_ACCOUNT.unfreezeAccount(account.getId());

            verifyUnfreeze(account);
        }

        private void verifyUnfreeze(Account account) throws IOException, ExecutionException, InterruptedException {
            Account accountById = getAccountById(account.getId());

            assertFalse(accountById.getFrozen());
            verifyAccount(Currency.ETH, accountById);
        }
    }
}
