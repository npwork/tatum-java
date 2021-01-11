package io.tatum.transaction;

import com.vechain.thorclient.utils.crypto.ECKeyPair;
import io.tatum.model.request.TransferVet;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

public class VetTxTest {

    @Test
    public void testValidTransactionDataWithFeeEstimation() throws InterruptedException, ExecutionException, IOException {

        ECKeyPair ecKeyPair = new ECKeyPair(new BigInteger("110478001"));
        ECKeyPair des = new ECKeyPair(new BigInteger("110478002"));

        var body = new TransferVet();
        body.setFromPrivateKey(ecKeyPair.getPublicKey().toString(16));
        body.setAmount("0");
        body.setTo(des.getHexAddress());
        VetTx vetTx = new VetTx();
        String txData = vetTx.prepareVetSignedTransaction(true, body, null);
        assertTrue(txData.contains("0x"));
    }

}
