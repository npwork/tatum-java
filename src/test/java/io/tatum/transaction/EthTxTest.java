package io.tatum.transaction;

import io.tatum.model.request.Currency;
import io.tatum.model.request.DeployEthErc20;
import io.tatum.model.request.TransferCustomErc20;
import io.tatum.model.request.TransferEthErc20;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class EthTxTest {

    /**
     * Test with Ganache https://www.trufflesuite.com/ganache
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void should_valid_transaction_ETH_data() throws ExecutionException, InterruptedException {
        var body = new TransferEthErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setAmount("0.1");
        body.setCurrency(Currency.ETH);
        body.setTo("0x68075d7d281300F3486aaA5C4b7E43F38afA3b72");
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareEthOrErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData.contains("0x"));
    }

    @Test
    public void should_test_valid_transaction_ERC20_data() throws ExecutionException, InterruptedException {
        var body = new TransferEthErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setAmount("0.1");
        body.setCurrency(Currency.UNI);
        body.setTo("0x68075d7d281300F3486aaA5C4b7E43F38afA3b72");
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareEthOrErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData.contains("0x"));
    }

    @Test
    public void should_test_valid_custom_transaction_ERC20_data() throws InterruptedException, ExecutionException, IOException {
        var body = new TransferCustomErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setAmount("0.1");
        body.setContractAddress("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea");
        body.setTo("0x68075d7d281300F3486aaA5C4b7E43F38afA3b72");
        body.setDigits(10);
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareCustomErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData.contains("0x"));
    }

    @Test
    public void should_test_valid_custom_deployment_ERC20() throws InterruptedException, ExecutionException, IOException {
        var body = new DeployEthErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setSymbol("SYMBOL");
        body.setName("Test_ERC20");
        body.setSupply("100");
        body.setAddress("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea");
        body.setDigits(10);
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareDeployErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData.contains("0x"));
    }

    @Test
    public void should_test_invalid_custom_deployment_ERC20_missing_supply() throws InterruptedException, ExecutionException, IOException {
        var body = new DeployEthErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setSymbol("SYMBOL");
        body.setName("Test_ERC20");
        body.setAddress("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea");
        body.setDigits(10);
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareDeployErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData == null);
    }

    @Test
    public void should_test_invalid_custom_transaction_ERC20_data_missing_digits() throws InterruptedException, ExecutionException, IOException {
        var body = new DeployEthErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setSymbol("SYMBOL");
        body.setSupply("100");
        body.setName("Test_ERC20");
        body.setAddress("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea");
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareDeployErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData == null);
    }

    @Test
    public void should_not_test_valid_transaction_data_missing_currency() throws InterruptedException, ExecutionException {
        var body = new TransferEthErc20();
        body.setFromPrivateKey("0x20642cff687d1953264d3c4e7e4e942edbf3602709056fa6d6c1947447aab35d");
        body.setAmount("0.1");
        body.setTo("0x68075d7d281300F3486aaA5C4b7E43F38afA3b72");
        body.setNonce(BigInteger.ONE);
        String txData = new EthTx().prepareEthOrErc20SignedTransaction(body, "http://localhost:7545");
        assertTrue(txData == null);
    }
}
