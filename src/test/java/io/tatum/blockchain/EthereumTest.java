package io.tatum.blockchain;

import io.tatum.model.response.eth.EthBlock;
import io.tatum.model.response.eth.EthTx;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class EthereumTest {

    @Test
    public void ethGetTransactionsCountTest() throws InterruptedException, ExecutionException, IOException {
        Ethereum ethereum = new Ethereum();
        // https://etherscan.io/address/0xec5a91b107775d267879e8474ced81a70bcfd7a9
        String address = "0xec5a91b107775d267879e8474ced81a70bcfd7a9";
        BigDecimal count = ethereum.ethGetTransactionsCount(address);
        System.out.println(count);
    }

    @Test
    public void ethGetCurrentBlockTest() throws InterruptedException, ExecutionException, IOException {
        Ethereum ethereum = new Ethereum();
        BigDecimal block = ethereum.ethGetCurrentBlock();
        System.out.println(block);
    }

    @Test
    public void ethGetBlockTest() throws InterruptedException, ExecutionException, IOException {
        Ethereum ethereum = new Ethereum();
        // https://etherscan.io/block/11449144
        EthBlock block = ethereum.ethGetBlock("0x9f1f62a40f084b054400bbeaac766a4205a194761fa48297fd8b147cca6585e4");
        System.out.println(block);
        assertThat(block, hasProperty("extraData"));
        assertThat(block, hasProperty("gasLimit"));
        assertThat(block, hasProperty("gasUsed"));
        assertThat(block, hasProperty("difficulty"));
    }

    @Test
    public void ethGetAccountBalanceTest() throws InterruptedException, ExecutionException, IOException {
        Ethereum ethereum = new Ethereum();
        String address = "0x8d056d457a52c4daf71cef45f540a040c143ea05";
        BigDecimal balance = ethereum.ethGetAccountBalance(address);
        System.out.println(balance);
    }

    @Test
    public void ethGetAccountErc20AddressTest() throws InterruptedException, ExecutionException, IOException {
        Ethereum ethereum = new Ethereum();
        // https://etherscan.io/token/0xdac17f958d2ee523a2206206994597c13d831ec7?a=0x080d43de2c3059c30f95ab2eddcee0b0a0ddb539
        String address = "0x080d43DE2C3059c30f95AB2EdDcee0B0a0Ddb539";
        BigDecimal balance = ethereum.ethGetAccountErc20Address(address, "0xdac17f958d2ee523a2206206994597c13d831ec7");
        System.out.println(balance);
    }

    @Test
    public void ethGetTransactionTest() throws InterruptedException, ExecutionException, IOException {
        Ethereum ethereum = new Ethereum();
        // https://etherscan.io/tx/0xffd125fb072d2974c14576a2f777f3386535db5b85c0d120066dc8fbd5d7680a
        String hash = "0xffd125fb072d2974c14576a2f777f3386535db5b85c0d120066dc8fbd5d7680a";
        EthTx tx = ethereum.ethGetTransaction(hash);
        System.out.println(tx);
        assertThat(tx, hasProperty("blockHash"));
        assertThat(tx, hasProperty("from"));
        assertThat(tx, hasProperty("to"));
        assertThat(tx, hasProperty("blockNumber"));
    }

    @Test
    public void ethGetAccountTransactionsTest() throws ExecutionException, InterruptedException {

        Ethereum ethereum = new Ethereum();
        // https://etherscan.io/tx/0xffd125fb072d2974c14576a2f777f3386535db5b85c0d120066dc8fbd5d7680a
        String address = "0x0bE907184172706F8D5321E2DA108Ed190a8e59c";
        EthTx[] txs = ethereum.ethGetAccountTransactions(address, null, null);
        if (txs != null && txs.length > 0) {
            System.out.println(txs[0]);
            assertThat(txs[0], hasProperty("blockHash"));
            assertThat(txs[0], hasProperty("from"));
            assertThat(txs[0], hasProperty("to"));
            assertThat(txs[0], hasProperty("blockNumber"));
        }
    }
}
