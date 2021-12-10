package io.tatum.blockchain;

import io.tatum.model.response.btc.BtcBlock;
import io.tatum.model.response.btc.BtcInfo;
import io.tatum.model.response.btc.BtcTx;
import io.tatum.model.response.btc.BtcUTXO;
import io.tatum.model.response.common.BlockHash;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Bitcoin Testnet
 * <p>
 * https://www.blockchain.com/btc-testnet/block/0000000017cb55f6f5afd2d2f3ac72209edc502442879c59bf79abdc39e3a3ff
 * https://www.blockchain.com/btc-testnet/address/tb1q2kn7a3kc7hpltwpdxuydf65hn9w87hpqzgd3s5
 * https://www.blockchain.com/btc-testnet/tx/5a3a323f55d79cbd643160b237a6f69b3d0268617a84e8acc30812df87498361
 *
 */
public class BitcoinTest {
    private final static Bitcoin BITCOIN = new Bitcoin();

    private final static long BLOCK = 2106672;
    private final static String BLOCK_HASH = "0000000017cb55f6f5afd2d2f3ac72209edc502442879c59bf79abdc39e3a3ff";
    private final static String BLOCK_HASH_DOES_NOT_EXIST = "0000000017cb15f6f5afd3d1f3ac72209edc502442879c59bf79abdc39e3a3ff";
    public final static String ADDRESS = "tb1q2kn7a3kc7hpltwpdxuydf65hn9w87hpqzgd3s5";
    private final static String ADDRESS_DOES_NOT_EXIST = "tb1q2kn7a4kc7hpl2wpdxuydf65hn9w87hpqzgd3s5";
    private final static String ADDRESS_NO_TX = "tb1q7n5kx6thmwlq99kfznfex26um68tpazz7sjefk";
    private final static String TRANSACTION = "5a3a323f55d79cbd643160b237a6f69b3d0268617a84e8acc30812df87498361";
    private final static String TRANSACTION_DOES_NOT_EXIST = "5a4a323f55779cb2643160b237a6f69b3d0268617a84e8acc30812df87498361";

    @Test
    public void GetCurrentBlock() throws InterruptedException, ExecutionException {
        BtcInfo current = BITCOIN.btcGetCurrentBlock();
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
            BtcBlock expected = BtcBlock.builder()
                    .hash(BLOCK_HASH)
                    .nonce(4196691978L)
                    .merkleRoot("df57452fcd362ac702c9cb014b0567f3100c686c1772ab64d76143bc20fa4834")
                    .prevBlock("000000000000000747151a3c70c3661dc48a6807159a61e0dce8a84aaec2be55")
                    .version(536870912)
                    .height(2106672)
                    .depth(1)
                    .version(536870912)
                    .time(1639048880)
                    .bits(486604799)
                    .build();

            BtcBlock actual = BITCOIN.btcGetBlock(BLOCK_HASH);

            assertEquals(106, actual.getTxs().length);

            actual.setTxs(null);
            assertEquals(expected, actual);
        }

        @Test
        public void blockDoesNotExist() throws InterruptedException, ExecutionException {
            BtcBlock actual = BITCOIN.btcGetBlock(BLOCK_HASH_DOES_NOT_EXIST);
            assertNull(actual);
        }
    }

    @Nested
    class GetBlockHash {
        @Test
        public void blockExists() throws InterruptedException, ExecutionException {
            BlockHash hash = BITCOIN.btcGetBlockHash(BLOCK);
            assertEquals(new BlockHash(BLOCK_HASH), hash);
        }

        @Test
        public void blockDoesNotExist() throws InterruptedException, ExecutionException {
            BlockHash hash = BITCOIN.btcGetBlockHash(Long.MAX_VALUE);
            assertNull(hash);
        }
    }

    @Nested
    class GetUTXO {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            BtcUTXO expected = BtcUTXO.builder()
                    .index(0)
                    .address(ADDRESS)
                    .coinbase(false)
                    .version(2)
                    .height(2106676)
                    .value(new BigDecimal(0.0005).scaleByPowerOfTen(8).setScale(0, RoundingMode.FLOOR))
                    .script("001455a7eec6d8f5c3f5b82d3708d4ea97995c7f5c20")
                    .hash(TRANSACTION)
                    .build();

            BtcUTXO utxo = BITCOIN.btcGetUTXO(TRANSACTION, 0);
            assertEquals(expected, utxo);
        }

        @Test
        public void utxoDoesntExist() throws InterruptedException, ExecutionException {
            BtcUTXO utxo = BITCOIN.btcGetUTXO(TRANSACTION, 100);
            assertNull(utxo);
        }

        @Test
        public void txDoesNotExist() throws InterruptedException, ExecutionException {
            BtcUTXO utxo = BITCOIN.btcGetUTXO(TRANSACTION_DOES_NOT_EXIST, 0);
            assertNull(utxo);
        }
    }

    @Nested
    class GetTXForAccount {
        @Test
        public void accountExists() throws InterruptedException, ExecutionException {
            BtcTx[] txs = BITCOIN.btcGetTxForAccount(ADDRESS);
            assertEquals(3, txs.length);
        }

        @Test
        public void accountDoesNotExist() throws InterruptedException, ExecutionException {
            BtcTx[] txs = BITCOIN.btcGetTxForAccount(ADDRESS_DOES_NOT_EXIST);
            assertEquals(0, txs.length);
        }

        @Test
        public void noTxs() throws InterruptedException, ExecutionException {
            BtcTx[] txs = BITCOIN.btcGetTxForAccount(ADDRESS_NO_TX);
            assertEquals(0, txs.length);
        }
    }

    @Nested
    class GetTX {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            BtcTx expected = BtcTx.builder()
                    .hash(TRANSACTION)
                    .blockNumber(2106676)
                    .witnessHash("575cf1274957bdce91a569a7f007d501c0e790c4e90f079503df3225a82c1e78")
                    .fee(new BigDecimal(141))
                    .rate(new BigDecimal(1000))
                    .mtime(1639051824)
                    .height(0)
                    .index(51)
                    .version(2)
                    .locktime(2106675)
                    .time(1639050756)
                    .build();

            BtcTx tx = BITCOIN.btcGetTransaction(TRANSACTION);

            assertEquals(1, tx.getInputs().length);
            assertEquals(2, tx.getOutputs().length);

            tx.setInputs(null);
            tx.setOutputs(null);
            assertEquals(expected, tx);
        }

        @Test
        public void txDoesntExist() throws InterruptedException, ExecutionException {
            BtcTx tx = BITCOIN.btcGetTransaction(TRANSACTION_DOES_NOT_EXIST);
            assertNull(tx);
        }
    }
}
