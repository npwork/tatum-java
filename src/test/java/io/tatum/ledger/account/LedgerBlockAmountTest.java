package io.tatum.ledger.account;

import io.tatum.model.request.BlockAmount;
import io.tatum.model.request.Currency;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.Blockage;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class LedgerBlockAmountTest extends AbstractLedgerAccountTest {
    private static final BlockAmount BLOCK_AMOUNT_1 = new BlockAmount("10", "type", null);
    private static final BlockAmount BLOCK_AMOUNT_2 = new BlockAmount("20", "type2", null);

    @Test
    public void empty() throws IOException, ExecutionException, InterruptedException {
        Account account = createAccountWithCurrency(Currency.ETH);
        Blockage[] blockage = LEDGER_ACCOUNT.getBlockedAmountsByAccountId(account.getId());
        assertEquals(0, blockage.length);
    }

    @Nested
    class BlockageAmount {
        @Test
        public void invalid() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            assertNull(LEDGER_ACCOUNT.blockAmount(account.getId(), new BlockAmount("abc", "type", null)));
        }

        @Test
        public void single() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            String blockAmountId = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);

            Blockage[] expected = {new Blockage(blockAmountId, account.getId(), BLOCK_AMOUNT_1.getAmount(), BLOCK_AMOUNT_1.getType(), null)};

            verifyBlockages(account, expected);
        }

        @Test
        public void multipleSameType() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            String blockAmountId1 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);
            String blockAmountId2 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);

            Blockage[] expected = {new Blockage(blockAmountId1, account.getId(), BLOCK_AMOUNT_1.getAmount(), BLOCK_AMOUNT_1.getType(), null), new Blockage(blockAmountId2, account.getId(), BLOCK_AMOUNT_1.getAmount(), BLOCK_AMOUNT_1.getType(), null)};

            verifyBlockages(account, expected);
        }

        @Test
        public void multipleDifferentTypes() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            String blockAmountId1 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);
            String blockAmountId2 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_2);

            Blockage[] expected = {
                    new Blockage(blockAmountId2, account.getId(), BLOCK_AMOUNT_2.getAmount(), BLOCK_AMOUNT_2.getType(), null),
                    new Blockage(blockAmountId1, account.getId(), BLOCK_AMOUNT_1.getAmount(), BLOCK_AMOUNT_1.getType(), null)
            };

            verifyBlockages(account, expected);
        }
    }

    @Nested
    class DeleteBlockageAmount {
        @Test
        public void single() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            String blockAmountId = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);
            LEDGER_ACCOUNT.deleteBlockedAmount(blockAmountId);

            verifyBlockages(account, new Blockage[0]);
        }

        @Test
        public void deleteOneOutOfTwo() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            String blockAmountId1 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);
            String blockAmountId2 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_2);

            LEDGER_ACCOUNT.deleteBlockedAmount(blockAmountId1);

            Blockage[] expected = {new Blockage(blockAmountId2, account.getId(), BLOCK_AMOUNT_2.getAmount(), BLOCK_AMOUNT_2.getType(), null)};

            verifyBlockages(account, expected);
        }

        @Test
        public void deleteTwoOutOfTwo() throws IOException, ExecutionException, InterruptedException {
            Account account = createAccountWithCurrency(Currency.ETH);
            String blockAmountId1 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_1);
            String blockAmountId2 = LEDGER_ACCOUNT.blockAmount(account.getId(), BLOCK_AMOUNT_2);

            LEDGER_ACCOUNT.deleteBlockedAmount(blockAmountId1);
            LEDGER_ACCOUNT.deleteBlockedAmount(blockAmountId2);

            verifyBlockages(account, new Blockage[0]);
        }
    }

    private void verifyBlockages(Account account, Blockage[] expected) throws ExecutionException, InterruptedException {
        Blockage[] blockage = LEDGER_ACCOUNT.getBlockedAmountsByAccountId(account.getId());
        assertArrayEquals(expected, blockage);
    }
}
