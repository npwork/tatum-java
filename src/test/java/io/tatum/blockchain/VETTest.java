package io.tatum.blockchain;

import io.tatum.model.request.EstimateGasVet;
import io.tatum.model.response.vet.VetBlock;
import io.tatum.model.response.vet.VetEstimateGas;
import io.tatum.model.response.vet.VetTx;
import io.tatum.model.response.vet.VetTxReceipt;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class VETTest {

    @Test
    public void vetEstimateGasTest() throws InterruptedException, ExecutionException, IOException {
        VET vet = new VET();
        EstimateGasVet body = new EstimateGasVet();
        body.setData("afsdfhsdfhs");
        body.setFrom("sduifgsdiufhdsihfdsihfdshfdshfdshfdfpofrdsfdsfds");
        body.setTo("efdfdfdfdfdsfdfdfdfgfgfgfdg");
        body.setNonce(100);
        body.setValue("dsfdsfdfdsfdggfgf");
        VetEstimateGas vetEstimateGas = vet.vetEstimateGas(body);
    }

    @Test
    public void vetGetCurrentBlockTest() throws ExecutionException, InterruptedException {
        VET vet = new VET();
        BigDecimal block = vet.vetGetCurrentBlock();
        System.out.println(block);
    }

    @Test
    public void vetGetBlockTest() throws ExecutionException, InterruptedException, IOException {
        VET vet = new VET();
        // https://explore.vechain.org/blocks/0x007626454021a171f4afc50af1a46418320fa761536eace78ee8c4f252a51389/
        String hash = "0x007626454021a171f4afc50af1a46418320fa761536eace78ee8c4f252a51389";
        VetBlock block = vet.vetGetBlock(hash);
        System.out.println(block);
        assertThat(block, hasProperty("number"));
        assertThat(block, hasProperty("parentID"));
        assertThat(block, hasProperty("size"));
        assertThat(block, hasProperty("timestamp"));
    }

    @Test
    public void vetGetAccountBalanceTest() throws ExecutionException, InterruptedException, IOException {
        VET vet = new VET();
        // https://explore.vechain.org/accounts/0x0f296031669698427aff5df1e186794d182a583d/
        String address = "0x0F296031669698427aFF5Df1e186794d182a583D";
        BigDecimal balance = vet.vetGetAccountBalance(address);
        System.out.println(balance);
    }

    @Test
    public void vetGetAccountEnergyTest() throws ExecutionException, InterruptedException, IOException {
        VET vet = new VET();
        // https://explore.vechain.org/accounts/0x0f296031669698427aff5df1e186794d182a583d/
        String address = "0x0F296031669698427aFF5Df1e186794d182a583D";
        BigDecimal energy = vet.vetGetAccountEnergy(address);
        System.out.println(energy);
    }

    @Test
    public void vetGetTransactionTest() throws ExecutionException, InterruptedException, IOException {
        VET vet = new VET();
        // https://explore.vechain.org/transactions/0x226c64b8622997b8f88fda3935588d34d0b744fab16e5710a7722889317f4872#info
        String tx = "0x226c64b8622997b8f88fda3935588d34d0b744fab16e5710a7722889317f4872";
        VetTx vetTx = vet.vetGetTransaction(tx);
        System.out.println(vetTx);
        assertThat(vetTx, hasProperty("chainTag"));
        assertThat(vetTx, hasProperty("expiration"));
        assertThat(vetTx, hasProperty("clauses"));
        assertThat(vetTx, hasProperty("blockRef"));
    }

    @Test
    public void vetGetTransactionReceiptTest() throws ExecutionException, InterruptedException, IOException {
        VET vet = new VET();
        // https://explore.vechain.org/transactions/0x226c64b8622997b8f88fda3935588d34d0b744fab16e5710a7722889317f4872#info
        String txHash = "0x226c64b8622997b8f88fda3935588d34d0b744fab16e5710a7722889317f4872";
        VetTxReceipt vetTxReceipt = vet.vetGetTransactionReceipt(txHash);
        System.out.println(vetTxReceipt);
        System.out.println(vetTxReceipt.getOutputs()[0].getEvents()[0]);
        assertThat(vetTxReceipt, hasProperty("gasUsed"));
        assertThat(vetTxReceipt, hasProperty("gasPayer"));
        assertThat(vetTxReceipt, hasProperty("paid"));
        assertThat(vetTxReceipt, hasProperty("reward"));
    }
}
