package io.tatum.ledger;

import io.tatum.model.request.CreateTransaction;
import io.tatum.model.request.TransactionFilter;
import io.tatum.model.response.ledger.Reference;
import io.tatum.model.response.ledger.Transaction;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.ObjectValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * The type Ledger transaction.
 */
public class LedgerTransaction {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByReference" target="_blank">Tatum API documentation</a>
     *
     * @param reference the reference
     * @return the transaction [ ]
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Transaction[] getTransactionsByReference(String reference) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/reference/" + reference;
        return Async.get(uri, Transaction[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/sendTransaction" target="_blank">Tatum API documentation</a>
     *
     * @param transaction the transaction
     * @return the string
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String storeTransaction(CreateTransaction transaction) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(transaction)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction";
        var ref = Async.post(uri, transaction, Reference.class);
        return ref != null ? ref.getReference() : null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByAccountId" target="_blank">Tatum API documentation</a>
     *
     * @param filter   the filter
     * @param pageSize the page size
     * @param offset   the offset
     * @return the transaction [ ]
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Transaction[] getTransactionsByAccount(TransactionFilter filter, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/account?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.post(uri, filter, Transaction[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByCustomerId" target="_blank">Tatum API documentation</a>
     *
     * @param filter   the filter
     * @param pageSize the page size
     * @param offset   the offset
     * @return the transaction [ ]
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Transaction[] getTransactionsByCustomer(TransactionFilter filter, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/customer?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.post(uri, filter, Transaction[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactions" target="_blank">Tatum API documentation</a>
     *
     * @param filter   the filter
     * @param pageSize the page size
     * @param offset   the offset
     * @return the transaction [ ]
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Transaction[] getTransactionsByLedger(TransactionFilter filter, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/ledger?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.post(uri, filter, Transaction[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByAccountId" target="_blank">Tatum API documentation</a>
     *
     * @param filter the filter
     * @return the big decimal
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal countTransactionsByAccount(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/account?count=true";
        return Async.post(uri, filter, BigDecimal.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByCustomerId" target="_blank">Tatum API documentation</a>
     *
     * @param filter the filter
     * @return the big decimal
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal countTransactionsByCustomer(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/customer?count=true";
        return Async.post(uri, filter, BigDecimal.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactions" target="_blank">Tatum API documentation</a>
     *
     * @param filter the filter
     * @return the big decimal
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal countTransactionsByLedger(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/ledger?count=true";
        return Async.post(uri, filter, BigDecimal.class);
    }
}
