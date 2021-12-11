package io.tatum.blockchain;

import io.tatum.AbstractApiTest;
import io.tatum.model.response.common.Balance;
import io.tatum.model.response.eth.EthBlock;
import io.tatum.model.response.eth.EthTx;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ropsten testnet
 * <p>
 * https://ropsten.etherscan.io/block/10995793
 * https://ropsten.etherscan.io/address/0x44373FD089C81a5F4634F655cb73598B0E2F7799
 * https://ropsten.etherscan.io/tx/0x52fcb688992398b5c517f70ad7f1828d08ca0d896686f72ce02823700ff502ec
 */
public class EthereumTest extends AbstractApiTest {
    private final static BigDecimal BLOCK_NUMBER = new BigDecimal(10995793);

    public final static String ADDRESS = "0x44373FD089C81a5F4634F655cb73598B0E2F7799";
    private final static String ADDRESS_WITHOUT_TXS = "0x037271F80b60f4F67F89054E16d0e9f82cFaa237";
    private final static String ADDRESS_DOES_NOT_EXIST = "0x413723D459C67a5F4634F655cb73598B0E2F7799";

    private final static String ERC20_CONTRACT = "0x101848D5C5bBca18E6b4431eEdF6B95E9ADF82FA";
    private final static String ERC20_CONTRACT_DOES_NOT_EXIST = "0x121446D715bBca18E624433dEdF6B95E9ADF82FA";

    private final static String ETH_TRANSACTION = "0x52fcb688992398b5c517f70ad7f1828d08ca0d896686f72ce02823700ff502ec";
    private final static String ETH_TRANSACTION_DOES_NOT_EXIST = "0x52fcb688292338b55517f705d7f1858d085a0d895686f72ce02823700ff502ec";

    @Nested
    class GetTransactionsCount {
        @Disabled("Flaky - sometimes 0 sometimes null") // @TODO
        @Test
        public void noPendingTransactions() throws IOException {
            Long count = tatumApi.ethereum.transactionCount(ADDRESS).execute().body();
            assertEquals(0, count);
        }

        @Test
        public void addressDoesNotExist() throws IOException {
            Long count = tatumApi.ethereum.transactionCount(ADDRESS_DOES_NOT_EXIST).execute().body();
            assertNull(count);
        }

        // @TODO - add testcase when in mempool
    }

    @Test
    public void GetCurrentBlock() throws IOException {
        Long currentBlock = tatumApi.ethereum.currentBlock().execute().body();
        assertTrue(currentBlock > BLOCK_NUMBER.longValue());
    }

    @Nested
    class EthGetBlock {
        @Test
        public void blockExists() throws IOException {
            EthBlock actual = tatumApi.ethereum.getBlock(BLOCK_NUMBER.toString()).execute().body();

            EthBlock expected = EthBlock.builder().number(BLOCK_NUMBER).transactionsRoot("0xfc099c56b493e5923b16e918b3d7d4ef63a73ab89b264d5a928040290e7a199e").totalDifficulty(new BigDecimal("34720647413045951")).gasLimit(new BigDecimal(8_000_000)).gasUsed(new BigDecimal(4_765_083)).difficulty(new BigDecimal(1060617355)).hash("0xa12dd4f56064bf056dd0a4b6f922894f61dd6d1bad9c929709d7f9941ae83945").parentHash("0x34bdac0dd4a24b8affb17212fedf2beedc173367d60cc2a48a4f722fa3c76468").stateRoot("0xa0de96327cc839e103c8dd360847a44a6ee3307c153990f44d63f992edfd6dae").sha3Uncles("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347").nonce("0x6998cda2384b82b6").miner("0x9ffed2297c7b81293413550db675073ab46980b2").size(new BigDecimal(23306)).extraData("0xd883010a08846765746888676f312e31362e35856c696e7578").timestamp(1631093664).build();

            assertNotNull(actual.getLogsBloom());
            assertEquals(13, actual.getTransactions().length);

            actual.setTransactions(null);
            actual.setLogsBloom(null);

            assertEquals(expected, actual);
        }

        @Test
        public void blockDoesNotExist() throws IOException {
            EthBlock actual = tatumApi.ethereum.getBlock("0102109957931").execute().body();
            assertNull(actual);
        }
    }

    @Nested
    class GetAccountBalance {
        @Test
        public void addressExists() throws IOException {
            Balance balance = tatumApi.ethereum.getAccountBalance(ADDRESS).execute().body();
            assertEquals(new BigDecimal(0.39).setScale(2, RoundingMode.FLOOR), balance.getBalance());
        }

        @Test
        public void addressDoesNotExist() throws IOException {
            Balance balance = tatumApi.ethereum.getAccountBalance(ADDRESS_DOES_NOT_EXIST).execute().body();
            assertNull(balance);
        }
    }

    @Nested
    class GetErc20AccountBalance {
        @Test
        public void addressAndContractExist() throws IOException {
            Balance balance = tatumApi.ethereum.getAccountBalance(ADDRESS, ERC20_CONTRACT).execute().body();
            BigDecimal amountWithoutZeros = balance.getBalance().scaleByPowerOfTen(-18).setScale(0);
            assertEquals(new BigDecimal(100), amountWithoutZeros);
        }

        @Test
        public void addressDoesNotExist() throws IOException {
            Balance balance = tatumApi.ethereum.getAccountBalance(ADDRESS_DOES_NOT_EXIST, ERC20_CONTRACT).execute().body();
            assertNull(balance);
        }

        @Test
        public void contractDoesNotExist() throws IOException {
            Balance balance = tatumApi.ethereum.getAccountBalance(ADDRESS, ERC20_CONTRACT_DOES_NOT_EXIST).execute().body();
            assertNull(balance);
        }
    }

    @Nested
    class GetTransaction {
        @Test
        public void exist() throws IOException {
            EthTx expected = EthTx.builder().status(true).blockNumber(BigDecimal.valueOf(10995793)).blockHash("0xa12dd4f56064bf056dd0a4b6f922894f61dd6d1bad9c929709d7f9941ae83945").from("0xe816846dde5d77bbfa5593d89242e05c6851917b").gas(BigDecimal.valueOf(21000)).gasUsed(BigDecimal.valueOf(21000)).gasPrice(BigDecimal.valueOf(1500000585)).transactionHash(ETH_TRANSACTION).transactionIndex(BigDecimal.valueOf(6)).nonce(BigDecimal.valueOf(13)).to("0x44373fd089c81a5f4634f655cb73598b0e2f7799").value(BigDecimal.valueOf(0.01).scaleByPowerOfTen(18).setScale(0)).logs(new ArrayList<>()).build();

            EthTx actual = tatumApi.ethereum.getTransaction(ETH_TRANSACTION).execute().body();
            assertEquals(expected, actual);
        }

        @Test
        public void doesNotExist() throws IOException {
            EthTx actual = tatumApi.ethereum.getTransaction(ETH_TRANSACTION_DOES_NOT_EXIST).execute().body();
            assertNull(actual);
        }
    }

    @Nested
    class GetAccountTransactions {
        @Test
        public void hasTransactions() throws IOException {
            List<EthTx> txs = tatumApi.ethereum.getTransactionsByAddress(ADDRESS).execute().body();
            assertEquals(11, txs.size());
        }

        @Test
        public void noTransactions() throws IOException {
            List<EthTx> txs = tatumApi.ethereum.getTransactionsByAddress(ADDRESS_WITHOUT_TXS).execute().body();
            assertEquals(0, txs.size());
        }

        @Test
        public void addressDoesNotExist() throws IOException {
            List<EthTx> txs = tatumApi.ethereum.getTransactionsByAddress(ADDRESS_DOES_NOT_EXIST).execute().body();
            assertEquals(0, txs.size());
        }
    }
}
