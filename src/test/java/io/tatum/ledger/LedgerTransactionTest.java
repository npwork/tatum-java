package io.tatum.ledger;

import io.tatum.model.request.CreateTransaction;
import io.tatum.model.request.TransactionFilter;
import io.tatum.model.response.ledger.Transaction;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class LedgerTransactionTest {

    @Test
    public void storeTransactionTest() throws InterruptedException, ExecutionException, IOException {
        CreateTransaction transaction = new CreateTransaction();
        transaction.setSenderAccountId("5e6645712b55823de7ea82f1");
        transaction.setRecipientAccountId("5e6645712b55823de7ea82f2");
        transaction.setAmount("5");
        String ref = new LedgerTransaction().storeTransaction(transaction);
    }

    @Test
    public void getTransactionsByAccountTest() throws InterruptedException, ExecutionException, IOException {
        TransactionFilter filter = new TransactionFilter();
        filter.setId("5e6be8e9e6aa436299950c41");
        Integer pageSize = 51;
        Integer offset = 0;
        Transaction[] transactions = new LedgerTransaction().getTransactionsByAccount(filter, pageSize, offset);
        System.out.println(Arrays.toString(transactions));
    }

    @Test
    public void getTransactionsByCustomerTest() throws InterruptedException, ExecutionException, IOException {
        TransactionFilter filter = new TransactionFilter();
        filter.setId("5e6be8e9e6aa436299950c41");
        Integer pageSize = 51;
        Integer offset = 0;
        Transaction[] transactions = new LedgerTransaction().getTransactionsByCustomer(filter, pageSize, offset);
        System.out.println(Arrays.toString(transactions));
    }

    @Test
    public void getTransactionsByLedgerTest() throws InterruptedException, ExecutionException, IOException {
        TransactionFilter filter = new TransactionFilter();
        filter.setId("5e6be8e9e6aa436299950c41");
        Integer pageSize = 51;
        Integer offset = 0;
        Transaction[] transactions = new LedgerTransaction().getTransactionsByLedger(filter, pageSize, offset);
        System.out.println(Arrays.toString(transactions));
    }

    @Test
    public void countTransactionsByAccountTest() throws InterruptedException, ExecutionException, IOException {
        TransactionFilter filter = new TransactionFilter();
        filter.setId("5e6be8e9e6aa436299950c41");
        BigDecimal count = new LedgerTransaction().countTransactionsByAccount(filter);
        System.out.println(count);
    }

    @Test
    public void countTransactionsByCustomerTest() throws InterruptedException, ExecutionException, IOException {
        TransactionFilter filter = new TransactionFilter();
        filter.setId("5e6be8e9e6aa436299950c41");
        BigDecimal count = new LedgerTransaction().countTransactionsByCustomer(filter);
        System.out.println(count);
    }

    @Test
    public void countTransactionsByLedgerTest() throws InterruptedException, ExecutionException, IOException {
        TransactionFilter filter = new TransactionFilter();
        filter.setId("5e6be8e9e6aa436299950c41");
        BigDecimal count = new LedgerTransaction().countTransactionsByLedger(filter);
        System.out.println(count);
    }

}
