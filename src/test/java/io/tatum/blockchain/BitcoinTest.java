package io.tatum.blockchain;

import io.tatum.AbstractApiTest;
import io.tatum.api.TatumApi;
import io.tatum.model.response.btc.BtcBlock;
import io.tatum.model.response.btc.BtcInfo;
import io.tatum.model.response.btc.BtcTx;
import io.tatum.model.response.btc.BtcUTXO;
import io.tatum.model.response.common.AddressResponse;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.CreateWalletResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Bitcoin Testnet
 * <p>
 * https://www.blockchain.com/btc-testnet/block/0000000017cb55f6f5afd2d2f3ac72209edc502442879c59bf79abdc39e3a3ff
 * https://www.blockchain.com/btc-testnet/address/tb1q2kn7a3kc7hpltwpdxuydf65hn9w87hpqzgd3s5
 * https://www.blockchain.com/btc-testnet/tx/5a3a323f55d79cbd643160b237a6f69b3d0268617a84e8acc30812df87498361
 */
public class BitcoinTest extends AbstractApiTest {
    private final static long BLOCK = 2106672;
    private final static String BLOCK_HASH = "0000000017cb55f6f5afd2d2f3ac72209edc502442879c59bf79abdc39e3a3ff";
    private final static String BLOCK_HASH_DOES_NOT_EXIST = "0000000017cb15f6f5afd3d1f3ac72209edc502442879c59bf79abdc39e3a3ff";
    public final static String ADDRESS = "tb1q2kn7a3kc7hpltwpdxuydf65hn9w87hpqzgd3s5";
    private final static String ADDRESS_DOES_NOT_EXIST = "tb1q2kn7a4kc7hpl2wpdxuydf65hn9w87hpqzgd3s5";
    private final static String ADDRESS_NO_TX = "tb1q7n5kx6thmwlq99kfznfex26um68tpazz7sjefk";
    private final static String TRANSACTION = "5a3a323f55d79cbd643160b237a6f69b3d0268617a84e8acc30812df87498361";
    private final static String TRANSACTION_DOES_NOT_EXIST = "5a4a323f55779cb2643160b237a6f69b3d0268617a84e8acc30812df87498361";

    private final static String MNEMONIC = "perfect copper topple tobacco throw trust maze whisper physical wise rose venture plate pepper celery install account bless inquiry dignity grocery park kid casino";
    private final static String XPUB = "tpubDEG8Epb5yDhugctraPNPvhk2vtCf9S3DuASHbLeYHXmuJx5Du56vaFow38k1e2HLyTcXTuEGKe9Yfc883EJoTkUmNZB8U1AQUWqGDh8ahD2";


    @Test
    public void generateWallet() throws IOException {
        CreateWalletResponse createdWallet = tatumApi.bitcoin.generateWallet().execute().body();
        assertNotNull(createdWallet.getMnemonic());
        assertNotNull(createdWallet.getXpub());
    }

    @Test
    public void addressFromXpub() throws IOException {
        AddressResponse zero = tatumApi.bitcoin.addressFromXpub(XPUB, 0).execute().body();
        AddressResponse hundred = tatumApi.bitcoin.addressFromXpub(XPUB, 100).execute().body();

        assertEquals("n3XmrqAaLvgPvsQAAFwrCYq8GGri4C1u9j", zero.getAddress());
        assertEquals("n1FHsqrKfDvo3363npe8Nn1cJBEX9KsMC8", hundred.getAddress());
    }

    @Test
    public void GetCurrentBlock() throws IOException {
        BtcInfo current = tatumApi.bitcoin.currentBlock().execute().body();
        assertTrue(current.getBlocks() > BLOCK);

        assertThat(current, hasProperty("chain"));
        assertThat(current, hasProperty("blocks"));
        assertThat(current, hasProperty("bestblockhash"));
        assertThat(current, hasProperty("difficulty"));
    }

    @Nested
    class GetBlock {
        @Test
        public void valid() throws IOException {
            BtcBlock expected = BtcBlock.builder().hash(BLOCK_HASH).nonce(4196691978L).merkleRoot("df57452fcd362ac702c9cb014b0567f3100c686c1772ab64d76143bc20fa4834").prevBlock("000000000000000747151a3c70c3661dc48a6807159a61e0dce8a84aaec2be55").version(536870912).height(2106672).depth(1).version(536870912).time(1639048880).bits(486604799).build();

            BtcBlock actual = tatumApi.bitcoin.getBlock(BLOCK_HASH).execute().body();

            assertEquals(106, actual.getTxs().length);

            actual.setTxs(null);
            assertEquals(expected, actual);
        }

        @Test
        public void blockDoesNotExist() throws IOException {
            BtcBlock actual = tatumApi.bitcoin.getBlock(BLOCK_HASH_DOES_NOT_EXIST).execute().body();
            assertNull(actual);
        }
    }

    @Nested
    class GetBlockHash {
        @Test
        public void blockExists() throws IOException {
            BlockHash hash = tatumApi.bitcoin.getBlockHash(BLOCK).execute().body();
            assertEquals(new BlockHash(BLOCK_HASH), hash);
        }

        @Test
        public void blockDoesNotExist() throws IOException {
            BlockHash hash = tatumApi.bitcoin.getBlockHash(Long.MAX_VALUE).execute().body();
            assertNull(hash);
        }
    }

    @Nested
    class GetUTXO {
        @Test
        public void valid() throws IOException {
            BtcUTXO expected = BtcUTXO.builder().index(0).address(ADDRESS).coinbase(false).version(2).height(2106676).value(new BigDecimal(0.0005).scaleByPowerOfTen(8).setScale(0, RoundingMode.FLOOR)).script("001455a7eec6d8f5c3f5b82d3708d4ea97995c7f5c20").hash(TRANSACTION).build();

            BtcUTXO utxo = tatumApi.bitcoin.getUtxo(TRANSACTION, 0).execute().body();
            assertEquals(expected, utxo);
        }

        @Test
        public void utxoDoesntExist() throws IOException {
            BtcUTXO utxo = tatumApi.bitcoin.getUtxo(TRANSACTION, 100).execute().body();
            assertNull(utxo);
        }

        @Test
        public void txDoesNotExist() throws IOException {
            BtcUTXO utxo = tatumApi.bitcoin.getUtxo(TRANSACTION_DOES_NOT_EXIST, 0).execute().body();
            assertNull(utxo);
        }
    }

    @Nested
    class GetTXForAccount {
        @Test
        public void accountExists() throws IOException {
            List<BtcTx> txs = tatumApi.bitcoin.getTxForAccount(ADDRESS).execute().body();
            assertEquals(3, txs.size());
        }

        @Test
        public void accountDoesNotExist() throws IOException {
            List<BtcTx> txs = tatumApi.bitcoin.getTxForAccount(ADDRESS_DOES_NOT_EXIST).execute().body();
            assertEquals(0, txs.size());
        }

        @Test
        public void noTxs() throws IOException {
            List<BtcTx> txs = tatumApi.bitcoin.getTxForAccount(ADDRESS_NO_TX).execute().body();
            assertEquals(0, txs.size());
        }
    }

    @Nested
    class GetTX {
        @Test
        public void valid() throws IOException {
            BtcTx expected = BtcTx.builder().hash(TRANSACTION).blockNumber(2106676).witnessHash("575cf1274957bdce91a569a7f007d501c0e790c4e90f079503df3225a82c1e78").fee(new BigDecimal(141)).rate(new BigDecimal(1000)).mtime(1639051824).height(0).index(51).version(2).locktime(2106675).time(1639050756).build();

            BtcTx tx = tatumApi.bitcoin.getTransaction(TRANSACTION).execute().body();

            assertEquals(1, tx.getInputs().length);
            assertEquals(2, tx.getOutputs().length);

            tx.setInputs(null);
            tx.setOutputs(null);
            assertEquals(expected, tx);
        }

        @Test
        public void txDoesntExist() throws IOException {
            BtcTx tx = tatumApi.bitcoin.getTransaction(TRANSACTION_DOES_NOT_EXIST).execute().body();
            assertNull(tx);
        }
    }
}
