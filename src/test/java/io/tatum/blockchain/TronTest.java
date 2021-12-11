package io.tatum.blockchain;

import io.tatum.AbstractApiTest;
import io.tatum.model.response.tron.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tron Shasta Testnet
 * <p>
 * https://shasta.tronscan.org/#/block/20555071
 * https://shasta.tronscan.org/#/address/TKAU1QN2McYpTVFR1t92KPzjpzytvfNzE9
 * https://shasta.tronscan.org/#/transaction/1eaad1926ab6d51c67c4d3203fa291543b19db589f6850024dbc243abe7d792f
 */
public class TronTest extends AbstractApiTest {
    public final static String ADDRESS = "TKAU1QN2McYpTVFR1t92KPzjpzytvfNzE9";
    private final static String ADDRESS_WITH_TRC10 = "TMZCJUzy98NBPw42537QsKxPuqbVuj1NZv";
    private final static String ADDRESS_WITH_TRC20 = "TScnb6dhpzZfVyFxYNv9pYyJ35qzfBoXQp";
    private final static String ADDRESS_DOES_NOT_EXIST = "TKAU2QN6McYpTVFR7t92KPzjpzytvfNzE9";
    private final static String TX = "1eaad1926ab6d51c67c4d3203fa291543b19db589f6850024dbc243abe7d792f";
    private final static String TX_DOESNT_EXIST = "1eaad1229ab6d51c62c4d3203fa291543b19db589f6850024dbc243abe7d792f";

    private final static Long BLOCK_NUMBER = 20_555_071L;
    private final static String BLOCK_HASH = "000000000139a53f23e649c438dbbd18db997e1934a6d8b7a1bb06da9fc24fc3";

    private final static String BLOCK_DOESNT_EXIST = "000000000139a53f63e149c428dbbd28db997e1934a6d8b7a1bb06da9fc24fc3";

    @Test
    public void getCurrentBlock() throws IOException {
        TronInfo info = tatumApi.tron.info().execute().body();
        assertTrue(info.getBlockNumber() > BLOCK_NUMBER);
        assertTrue(info.isTestnet());
        assertThat(info, hasProperty("hash"));
    }

    @Nested
    class GetBlock {
        @Test
        public void valid() throws IOException {
            TronBlock expected = TronBlock.builder()
                    .hash(BLOCK_HASH)
                    .blockNumber(BLOCK_NUMBER)
                    .timestamp(1639060314000L)
                    .parentHash("000000000139a53ec66dd2b3882354154577f1efc2714ed61711a5107fa77e1a")
                    .witnessAddress("41d0668c49826f2ca13e3848b5eb3414fb059b7cc0")
                    .witnessSignature("97f6385fa5985200c10cde87092306353c62c9880063f56dc2fb89367bf8261e6735f41371667bd37c2e5d2104d3f8b061f4f76f2d00adf1c2b424843b4fd6cc01")
                    .build();

            TronBlock block = tatumApi.tron.getBlock(BLOCK_HASH).execute().body();

            assertEquals(2, block.getTransactions().length);
            block.setTransactions(null);

            assertEquals(expected, block);
        }

        @Test
        public void notExist() throws IOException {
            TronBlock block = tatumApi.tron.getBlock(BLOCK_DOESNT_EXIST).execute().body();
            assertNull(block);
        }
    }

    @Nested
    class GetTransaction {
        @Test
        public void valid() throws IOException {
            TronTransaction expected = TronTransaction.builder()
                    .ret(new Ret[]{new Ret("SUCCESS", 0L)})
                    .signature(new String[]{"f6f6b13ac2de247319e582658598f34a797934f7f08f4ab6e9964cd5a64eefebe6d1f20a9a8a1732ae8be4eed66df2ec022903250681c0482c8cd4e33001263800"})
                    .txID(TX)
                    .netFee(100000L)
                    .netUsage(0L)
                    .energyFee(0L)
                    .energyUsage(0L)
                    .energyUsageTotal(0L)
                    .build();

            TronTransaction tx = tatumApi.tron.getTransaction(TX).execute().body();

            assertNotNull(tx.getRawData());
            tx.setRawData(null);

            assertEquals(expected, tx);
        }

        @Test
        public void notExist() throws IOException {
            TronTransaction tx = tatumApi.tron.getTransaction(TX_DOESNT_EXIST).execute().body();
            assertNull(tx.getTxID());
        }
    }

    @Nested
    class GetAccount {
        @Test
        public void valid() throws IOException {
            TronAccount expected = TronAccount.builder()
                    .balance(new BigDecimal(50).scaleByPowerOfTen(6).setScale(0, RoundingMode.FLOOR))
                    .createTime(1639060311000L)
                    .trc20(new HashMap[]{})
                    .build();

            TronAccount tronAccount = tatumApi.tron.getAccount(ADDRESS).execute().body();
            assertEquals(expected, tronAccount);
        }

        @Test
        public void validWithTrc10() throws IOException {
            TronAccount expected = TronAccount.builder()
                    .createTime(1594185723000L)
                    .trc20(new HashMap[]{})
                    .trc10(new Trc10[]{new Trc10("1000252", new BigDecimal(2.214).scaleByPowerOfTen(6).setScale(0, RoundingMode.HALF_EVEN))})
                    .build();

            TronAccount tronAccount = tatumApi.tron.getAccount(ADDRESS_WITH_TRC10).execute().body();
            assertEquals(expected, tronAccount);
        }

        @Test
        public void validWithTrc20() throws IOException {
            HashMap<String, BigDecimal> first = createMap("TDPnHo7BdAaXERFmMdvsYx7kssW4aaRrh5", BigDecimal.valueOf(999990809997588L));
            HashMap<String, BigDecimal> second = createMap("TFpiQ5iV3hHFzkVeMr7RLse76be5FqNnj6", BigDecimal.valueOf(981179054538361L));
            HashMap<String, BigDecimal> third = createMap("TRJaNt34a7uWqL1XsapU5f19joXeN3MPoL", new BigDecimal("999919700000000000002359296"));
            HashMap<String, BigDecimal> fourth = createMap("TRQSB9oxJ3ggPHySux8vjutyaxotSSK8aw", BigDecimal.valueOf(999999990000000L));

            TronAccount expected = TronAccount.builder()
                    .balance(new BigDecimal(19_059.2031).scaleByPowerOfTen(6).setScale(0, RoundingMode.HALF_EVEN))
                    .createTime(1599376824000L)
                    .trc20(new HashMap[]{first, second, third, fourth})
                    .build();


            TronAccount tronAccount = tatumApi.tron.getAccount(ADDRESS_WITH_TRC20).execute().body();
            assertEquals(expected, tronAccount);
        }

        @Test
        public void notFound() throws IOException {
            TronAccount tronAccount = tatumApi.tron.getAccount(ADDRESS_DOES_NOT_EXIST).execute().body();
            assertNull(tronAccount);
        }

        private HashMap<String, BigDecimal> createMap(String key, BigDecimal value) {
            HashMap<String, BigDecimal> first = new HashMap<>();
            first.put(key, value);
            return first;
        }
    }

    @Nested
    class GetTrc10Detail {
        @Test
        public void valid() throws IOException {
            long tokenId = 1000252;

            TronTrc10 expected = TronTrc10.builder()
                    .id(tokenId)
                    .precision(6L)
                    .description("BTFS TOKEN")
                    .abbr("BSN")
                    .name("BTFSTOKEN")
                    .totalSupply(new BigDecimal(100_000).scaleByPowerOfTen(6).setScale(0, RoundingMode.HALF_EVEN))
                    .ownerAddress("41a2468828043ee28b2c2556e063dad6a314372e2f")
                    .url("btfs.io")
                    .build();

            TronTrc10 tronTrc10 = tatumApi.tron.getTrc10Details(tokenId).execute().body();

            assertEquals(expected, tronTrc10);
        }

        @Test
        public void notFound() throws IOException {
            TronTrc10 tronTrc10 = tatumApi.tron.getTrc10Details(Long.MAX_VALUE).execute().body();
            assertNull(tronTrc10);
        }
    }
}
