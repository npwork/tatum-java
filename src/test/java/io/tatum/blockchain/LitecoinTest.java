package io.tatum.blockchain;

import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.ltc.LtcBlock;
import io.tatum.model.response.ltc.LtcInfo;
import io.tatum.model.response.ltc.LtcTx;
import io.tatum.model.response.ltc.LtcUTXO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Litecoin testnet
 * <p>
 * https://chain.so/block/LTCTEST/1fa8b4dc6612c07bcafeb7b2c34931b224e969f860b1a9606fe5b341928ec490
 * https://chain.so/address/LTCTEST/tltc1q05l3z297ypsge7cztya6hs7elyj7z9uk6s8szdc026jr6p7sdqsqe8smt2
 * https://chain.so/tx/LTCTEST/5e878df3e6308cd35f4da52075b4107ce57b74fff3f24815cfe0362f6034a81f
 */
public class LitecoinTest {
    private final static Litecoin LITECOIN = new Litecoin();
    public final static String ADDRESS = "tltc1q05l3z297ypsge7cztya6hs7elyj7z9uk6s8szdc026jr6p7sdqsqe8smt2";
    private final static String ADDRESS_DOES_NOT_EXIST = "tltc1q03l3z197ypsge2czzya6hs7elyj7z9uk6s8szdc026jr6p7sdqsqe8smt2";
    private final static String ADDRESS_NO_TX = "tltc1qem9sfcjvjcxacdta7cx3a20hm5djmfymvgwv3rc0t2mmd9vdy87qe0deuv";
    private final static String TX = "5e878df3e6308cd35f4da52075b4107ce57b74fff3f24815cfe0362f6034a81f";
    private final static String TX_DOESNT_EXIST = "5e872df3e6318cd35f4da22075b4507ce57b74fff3f24815cfe0362f6034a81f";

    private final static BigDecimal BLOCK_NUMBER = new BigDecimal(2_121_413);
    private final static String BLOCK_HASH = "1fa8b4dc6612c07bcafeb7b2c34931b224e969f860b1a9606fe5b341928ec490";

    private final static String BLOCK_DOESNT_EXIST = "1fa8b5dc6614c07bcafeb1b2c24931b214e969f860b1a9606fe5b341928ec490";

    @Test
    public void getCurrentBlock() throws InterruptedException, ExecutionException {
        LtcInfo info = LITECOIN.ltcGetCurrentBlock();
        assertTrue(info.getBlocks().compareTo(BLOCK_NUMBER) > 0);
        assertTrue(info.getHeaders().compareTo(BLOCK_NUMBER) > 0);

        assertThat(info, hasProperty("difficulty"));
        assertThat(info, hasProperty("bestblockhash"));
    }

    @Nested
    class GetBlock {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            LtcBlock expected = LtcBlock.builder()
                    .hash(BLOCK_HASH)
                    .height(BLOCK_NUMBER)
                    .version(536870912L)
                    .prevBlock("22eed1f84fcdff5cc55654f78ea4011f4f8e59727a2b3f692b8335c5f7beafb9")
                    .merkleRoot("d0005179da00d4bd67edca9d3886b061108bd7c6bed5c834d563260a6f611b81")
                    .ts(1639057194L)
                    .bits(487902576L)
                    .nonce(2619504603L)
                    .build();
            LtcBlock ltcBlock = LITECOIN.ltcGetBlock(BLOCK_HASH);

            assertEquals(6, ltcBlock.getTxs().length);
            ltcBlock.setTxs(null);

            assertEquals(expected, ltcBlock);
        }

        @Test
        public void notExist() throws ExecutionException, InterruptedException {
            LtcBlock ltcBlock = LITECOIN.ltcGetBlock(BLOCK_DOESNT_EXIST);
            assertNull(ltcBlock);
        }
    }

    @Nested
    class GetBlockHash {
        @Test
        public void valid() throws ExecutionException, InterruptedException {
            BlockHash blockHash = LITECOIN.ltcGetBlockHash(BLOCK_NUMBER);
            assertEquals(new BlockHash(BLOCK_HASH), blockHash);
        }

        @Test
        public void notExist() throws ExecutionException, InterruptedException {
            BlockHash blockHash = LITECOIN.ltcGetBlockHash(new BigDecimal(Long.MAX_VALUE));
            assertNull(blockHash);
        }
    }

    @Nested
    class GetUTXO {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            LtcUTXO expected = LtcUTXO.builder()
                    .index(0L)
                    .hash(TX)
                    .height(2121413L)
                    .value(new BigDecimal(0.25).scaleByPowerOfTen(8).setScale(0, RoundingMode.FLOOR))
                    .address(ADDRESS)
                    .coinbase(false)
                    .version(1L)
                    .script("00207d3f1128be20608cfb02593babc3d9f925e11796d40f01370f56a43d07d06820")
                    .build();

            LtcUTXO ltcUTXO = LITECOIN.ltcGetUTXO(TX, BigDecimal.ZERO);
            assertEquals(expected, ltcUTXO);
        }

        @Test
        public void utxoDoesntExist() throws InterruptedException, ExecutionException {
            LtcUTXO utxo = LITECOIN.ltcGetUTXO(TX, BigDecimal.TEN);
            assertNull(utxo);
        }

        @Test
        public void txDoesNotExist() throws InterruptedException, ExecutionException {
            LtcUTXO utxo = LITECOIN.ltcGetUTXO(TX_DOESNT_EXIST, BigDecimal.ZERO);
            assertNull(utxo);
        }
    }

    @Nested
    class GetTXForAccount {
        @Test
        public void accountExists() throws InterruptedException, ExecutionException {
            LtcTx[] txs = LITECOIN.ltcGetTxForAccount(ADDRESS);
            assertEquals(1, txs.length);
        }

        @Test
        public void accountDoesNotExist() throws InterruptedException, ExecutionException {
            LtcTx[] txs = LITECOIN.ltcGetTxForAccount(ADDRESS_DOES_NOT_EXIST);
            assertEquals(0, txs.length);
        }

        @Test
        public void noTxs() throws InterruptedException, ExecutionException {
            LtcTx[] txs = LITECOIN.ltcGetTxForAccount(ADDRESS_NO_TX);
            assertEquals(0, txs.length);
        }
    }

    @Nested
    class GetTX {
        @Test
        public void valid() throws InterruptedException, ExecutionException {
            LtcTx expected = LtcTx.builder()
                    .hash(TX)
                    .blockNumber(2121413L)
                    .witnessHash("20f0db2bd51216390e506d6682c6607702259bb3a1b51bccbf3c31536291e0e9")
                    .fee(BigDecimal.valueOf(0.0000434))
                    .rate(BigDecimal.valueOf(0.00020092))
                    .ps(1639057228L)
                    .index(3L)
                    .version(1L)
                    .flag(1L)
                    .locktime(0L)
                    .build();

            LtcTx tx = LITECOIN.ltcGetTransaction(TX);

            assertEquals(1, tx.getInputs().length);
            assertEquals(2, tx.getOutputs().length);

            tx.setInputs(null);
            tx.setOutputs(null);
            assertEquals(expected, tx);
        }

        @Test
        public void txDoesntExist() throws InterruptedException, ExecutionException {
            LtcTx tx = LITECOIN.ltcGetTransaction(TX_DOESNT_EXIST);
            assertNull(tx);
        }
    }
}
