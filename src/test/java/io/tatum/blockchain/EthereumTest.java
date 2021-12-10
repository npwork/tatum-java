package io.tatum.blockchain;

import io.tatum.model.response.eth.EthBlock;
import io.tatum.model.response.eth.EthTx;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ropsten testnet
 * <p>
 * https://ropsten.etherscan.io/block/10995793
 * https://ropsten.etherscan.io/address/0x44373FD089C81a5F4634F655cb73598B0E2F7799
 * https://ropsten.etherscan.io/tx/0x52fcb688992398b5c517f70ad7f1828d08ca0d896686f72ce02823700ff502ec
 */
public class EthereumTest {
    private final static Ethereum ETHERIUM = new Ethereum();

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
        public void noPendingTransactions() throws ExecutionException, InterruptedException {
            BigInteger count = ETHERIUM.ethGetTransactionsCount(ADDRESS);
            assertEquals(BigInteger.ZERO, count);
        }

        @Test
        public void addressDoesNotExist() throws ExecutionException, InterruptedException {
            BigInteger count = ETHERIUM.ethGetTransactionsCount(ADDRESS_DOES_NOT_EXIST);
            assertNull(count);
        }

        // @TODO - add testcase when in mempool
    }

    @Test
    public void GetCurrentBlock() throws InterruptedException, ExecutionException {
        BigDecimal block = ETHERIUM.ethGetCurrentBlock();
        assertTrue(block.compareTo(BLOCK_NUMBER) > 0);
    }

    @Nested
    class EthGetBlock {
        @Test
        public void blockExists() throws InterruptedException, ExecutionException {
            EthBlock actual = ETHERIUM.ethGetBlock(BLOCK_NUMBER.toString());

            EthBlock expected = EthBlock.builder()
                    .number(BLOCK_NUMBER)
                    .transactionsRoot("0xfc099c56b493e5923b16e918b3d7d4ef63a73ab89b264d5a928040290e7a199e")
                    .totalDifficulty(new BigDecimal("34720647413045951"))
                    .gasLimit(new BigDecimal(8_000_000))
                    .gasUsed(new BigDecimal(4_765_083))
                    .difficulty(new BigDecimal(1060617355))
                    .hash("0xa12dd4f56064bf056dd0a4b6f922894f61dd6d1bad9c929709d7f9941ae83945")
                    .parentHash("0x34bdac0dd4a24b8affb17212fedf2beedc173367d60cc2a48a4f722fa3c76468")
                    .stateRoot("0xa0de96327cc839e103c8dd360847a44a6ee3307c153990f44d63f992edfd6dae")
                    .sha3Uncles("0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347")
                    .nonce("0x6998cda2384b82b6")
                    .miner("0x9ffed2297c7b81293413550db675073ab46980b2")
                    .size(new BigDecimal(23306))
                    .extraData("0xd883010a08846765746888676f312e31362e35856c696e7578")
                    .timestamp(1631093664)
                    .build();

            assertNotNull(actual.getLogsBloom());
            assertEquals(13, actual.getTransactions().length);

            actual.setTransactions(null);
            actual.setLogsBloom(null);

            assertEquals(expected, actual);
        }

        @Test
        public void blockDoesNotExist() throws InterruptedException, ExecutionException {
            EthBlock actual = ETHERIUM.ethGetBlock("0102109957931");
            assertNull(actual);
        }
    }

    @Nested
    class GetAccountBalance {
        @Test
        public void addressExists() throws InterruptedException, ExecutionException {
            BigDecimal balance = ETHERIUM.ethGetAccountBalance(ADDRESS);
            assertEquals(new BigDecimal(0.39).setScale(2, RoundingMode.FLOOR), balance);
        }

        @Test
        public void addressDoesNotExist() throws InterruptedException, ExecutionException {
            BigDecimal balance = ETHERIUM.ethGetAccountBalance(ADDRESS_DOES_NOT_EXIST);
            assertNull(balance);
        }
    }

    @Nested
    class GetErc20AccountBalance {
        @Test
        public void addressAndContractExist() throws InterruptedException, ExecutionException, IOException {
            BigDecimal balance = ETHERIUM.ethGetAccountErc20Address(ADDRESS, ERC20_CONTRACT);
            BigDecimal amountWithoutZeros = balance.scaleByPowerOfTen(-18).setScale(0);
            assertEquals(new BigDecimal(100), amountWithoutZeros);
        }

        @Test
        public void addressDoesNotExist() throws InterruptedException, ExecutionException, IOException {
            BigDecimal balance = ETHERIUM.ethGetAccountErc20Address(ADDRESS_DOES_NOT_EXIST, ERC20_CONTRACT);
            assertNull(balance);
        }

        @Test
        public void contractDoesNotExist() throws InterruptedException, ExecutionException, IOException {
            BigDecimal balance = ETHERIUM.ethGetAccountErc20Address(ADDRESS, ERC20_CONTRACT_DOES_NOT_EXIST);
            assertNull(balance);
        }
    }

    @Nested
    class GetTransaction {
        @Test
        public void exist() throws InterruptedException, ExecutionException, IOException {
            EthTx expected = EthTx.builder()
                    .status(true)
                    .blockNumber(BigDecimal.valueOf(10995793))
                    .blockHash("0xa12dd4f56064bf056dd0a4b6f922894f61dd6d1bad9c929709d7f9941ae83945")
                    .from("0xe816846dde5d77bbfa5593d89242e05c6851917b")
                    .gas(BigDecimal.valueOf(21000))
                    .gasUsed(BigDecimal.valueOf(21000))
                    .gasPrice(BigDecimal.valueOf(1500000585))
                    .transactionHash(ETH_TRANSACTION)
                    .transactionIndex(BigDecimal.valueOf(6))
                    .nonce(BigDecimal.valueOf(13))
                    .to("0x44373fd089c81a5f4634f655cb73598b0e2f7799")
                    .value(BigDecimal.valueOf(0.01).scaleByPowerOfTen(18).setScale(0))
                    .logs(new ArrayList<>())
                    .build();

            EthTx actual = ETHERIUM.ethGetTransaction(ETH_TRANSACTION);
            assertEquals(expected, actual);
        }

        @Test
        public void doesNotExist() throws InterruptedException, ExecutionException, IOException {
            EthTx actual = ETHERIUM.ethGetTransaction(ETH_TRANSACTION_DOES_NOT_EXIST);
            assertNull(actual);
        }
    }

    @Nested
    class GetAccountTransactions {
        @Test
        public void hasTransactions() throws InterruptedException, ExecutionException, IOException {
            EthTx[] txs = ETHERIUM.ethGetAccountTransactions(ADDRESS);
            assertEquals(11, txs.length);
        }

        @Test
        public void noTransactions() throws InterruptedException, ExecutionException, IOException {
            EthTx[] txs = ETHERIUM.ethGetAccountTransactions(ADDRESS_WITHOUT_TXS);
            assertEquals(0, txs.length);
        }

        @Test
        public void addressDoesNotExist() throws InterruptedException, ExecutionException, IOException {
            EthTx[] txs = ETHERIUM.ethGetAccountTransactions(ADDRESS_DOES_NOT_EXIST);
            assertEquals(0, txs.length);
        }
    }
}
