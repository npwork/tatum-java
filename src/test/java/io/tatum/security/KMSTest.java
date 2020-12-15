package io.tatum.security;

import io.tatum.model.response.kms.TransactionKMS;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class KMSTest {

    @Test
    public void getTransactionKMSTest() throws InterruptedException, ExecutionException, IOException {
        KMS kms = new KMS();
        String id = "5e6645712b55823de7ea82f1";
        TransactionKMS transactionKMS = kms.getTransactionKMS(id);
        System.out.println(transactionKMS);

    }
}
