package io.tatum.transaction;

import io.tatum.model.request.TransferVet;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class VetTxTest {

    @Test
    public void testValidTransactionDataWithFeeEstimation() throws InterruptedException, ExecutionException, IOException {
        var body = new TransferVet();
        body.setFromPrivateKey("0x4874827a55d87f2309c55b835af509e3427aa4d52321eeb49a2b93b5c0f8edfb");
        body.setAmount("0");
        body.setTo("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea");
        VetTx vetTx = new VetTx();
        String txData = vetTx.prepareVetSignedTransaction(true, body, null);
        assertTrue(txData.contains("0x"));
    }

}
