package io.tatum.security;

import io.tatum.model.request.Currency;
import io.tatum.model.response.kms.TransactionKMS;
import org.checkerframework.checker.units.qual.K;
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

    @Test
    public void deleteTransactionKMSTest() throws InterruptedException, ExecutionException, IOException {
        KMS kms = new KMS();
        String id = "5e6645712b55823de7ea82f1";
        kms.deleteTransactionKMS(id, true);
    }

    @Test
    public void completePendingTransactionKMS() throws InterruptedException, ExecutionException, IOException {
        KMS kms = new KMS();
        String id = "5e6645712b55823de7ea82f1";
        String txId = "0x45587b0970c64966bd982450eb96869443ed0b93a1aa28f7f1630e8ccc52c637";
        kms.completePendingTransactionKMS(id, txId);
    }

    @Test
    public void getPendingTransactionsKMSByChainTest() throws InterruptedException, ExecutionException, IOException {
        KMS kms = new KMS();
        TransactionKMS[] transactionKMS = kms.getPendingTransactionsKMSByChain(Currency.ETH);
        if (transactionKMS.length > 0) {
            System.out.println(transactionKMS[0]);
        }
    }
}
