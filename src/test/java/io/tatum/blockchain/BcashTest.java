package io.tatum.blockchain;

import io.tatum.model.response.bch.BchBlock;
import io.tatum.model.response.bch.BchInfo;
import io.tatum.model.response.bch.BchTx;
import io.tatum.model.response.common.BlockHash;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Bitcoincash testnet
 * <p>
 * https://www.blockchain.com/bch-testnet/address/bchtest:qp260mkxmr6u8adc95ms3482j7v4cl6uyqealjarm6
 * https://www.blockchain.com/bch-testnet/tx/b32f8d83e3451dacbaffba89b9eac0d02b2a5fa24584deb4b724991a0724ff01
 * https://www.blockchain.com/bch-testnet/block/1477657
 */
public class BcashTest {
    private final static Bcash BCASH = new Bcash();

    private final static long BLOCK = 1477657;
    private final static String BLOCK_HASH = "00000000000000aae07c3d4f2400417709072cafa5b1b2779104ec593e2ff5b9";
    private final static String BLOCK_HASH_DOES_NOT_EXIST = "00000000000000aae01c4d4f3500517709072cafa5b1b2779104ec593e2ff5b9";
    public final static String ADDRESS = "bchtest:qp260mkxmr6u8adc95ms3482j7v4cl6uyqealjarm6";
    private final static String ADDRESS_DOES_NOT_EXIST = "bchtest:qp166mkxmr6u8adc21ms3482j7v4cl6uyqealjarm6";
    private final static String ADDRESS_NO_TX = "bchtest:qr0j3uv8xnp05rycj8nvn4798a375rzghs4m59a8k0";
    private final static String TRANSACTION = "b32f8d83e3451dacbaffba89b9eac0d02b2a5fa24584deb4b724991a0724ff01";
    private final static String TRANSACTION_DOES_NOT_EXIST = "b32f1d82e3451dacbaffba19b9eac0d02b2a5fa24584deb4b724991a0724ff01";

    @Test
    public void GetCurrentBlock() throws InterruptedException, ExecutionException {
        BchInfo current = BCASH.bcashGetCurrentBlock();
        assertTrue(current.getBlocks() > BLOCK);

        assertThat(current, hasProperty("chain"));
        assertThat(current, hasProperty("blocks"));
        assertThat(current, hasProperty("bestblockhash"));
        assertThat(current, hasProperty("difficulty"));
    }

    @Nested
    class GetBlock {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            BchBlock expected = BchBlock.builder()
                    .hash(BLOCK_HASH)
                    .height(BLOCK)
                    .nonce(1914471233L)
                    .difficulty(7647223L)
                    .size(410L)
                    .version(536870912)
                    .merkleroot("1a467d7571359c9f726294576bb49f825bc1a17b8d39c039abbfca31d52b9b51")
                    .previousblockhash("0000000000000130652d88448f775b1393c1f9d88e763a8ff50e530781598933")
                    .nextblockhash("000000000000010bc022e0b90f8c63c2cca74e52fcef1c0c8fda913b8d84f489")
                    .time(1639068982)
                    .bits("1a0231a1")
                    .versionHex("20000000")
                    .build();

            BchBlock actual = BCASH.bcashGetBlock(BLOCK_HASH);

            assertEquals(2, actual.getTx().length);
            assertTrue(actual.getConfirmations() > 0);
            actual.setTx(null);
            actual.setConfirmations(0);

            assertEquals(expected, actual);
        }

        @Test
        public void blockDoesNotExist() throws InterruptedException, ExecutionException {
            BchBlock actual = BCASH.bcashGetBlock(BLOCK_HASH_DOES_NOT_EXIST);
            assertNull(actual);
        }
    }

    @Nested
    class GetBlockHash {
        @Test
        public void blockExists() throws InterruptedException, ExecutionException {
            BlockHash hash = BCASH.bcashGetBlockHash(new BigDecimal(BLOCK));
            assertEquals(new BlockHash(BLOCK_HASH), hash);
        }

        @Test
        public void blockDoesNotExist() throws InterruptedException, ExecutionException {
            BlockHash hash = BCASH.bcashGetBlockHash(new BigDecimal(Long.MAX_VALUE));
            assertNull(hash);
        }
    }

    @Nested
    class GetTXForAccount {
        @Test
        public void accountExists() throws InterruptedException, ExecutionException {
            BchTx[] txs = BCASH.bcashGetTxForAccount(ADDRESS, BigDecimal.ZERO);
            assertEquals(1, txs.length);
        }

        @Test
        public void accountDoesNotExist() throws InterruptedException, ExecutionException {
            BchTx[] txs = BCASH.bcashGetTxForAccount(ADDRESS_DOES_NOT_EXIST, BigDecimal.ZERO);
            assertNull(txs);
        }

        @Test
        public void noTxs() throws InterruptedException, ExecutionException {
            BchTx[] txs = BCASH.bcashGetTxForAccount(ADDRESS_NO_TX, BigDecimal.ZERO);
            assertNull(txs);
        }
    }

    @Nested
    class GetTX {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            BchTx expected = BchTx.builder()
                    .txid(TRANSACTION)
                    .version(1)
                    .locktime(1477656)
                    .build();

            BchTx tx = BCASH.bcashGetTransaction(TRANSACTION);

            assertEquals(1, tx.getVin().length);
            assertEquals(2, tx.getVout().length);
            tx.setVin(null);
            tx.setVout(null);

            assertEquals(expected, tx);
        }

        @Test
        public void txDoesntExist() throws InterruptedException, ExecutionException {
            BchTx tx = BCASH.bcashGetTransaction(TRANSACTION_DOES_NOT_EXIST);
            assertNull(tx);
        }
    }
}
