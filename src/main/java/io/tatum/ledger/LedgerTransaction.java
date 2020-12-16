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

public class LedgerTransaction {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByReference" target="_blank">Tatum API documentation</a>
     */
    public Transaction[] getTransactionsByReference(String reference) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/reference/" + reference;
        return Async.get(uri, Transaction[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/sendTransaction" target="_blank">Tatum API documentation</a>
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
     */
    public BigDecimal countTransactionsByLedger(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(filter)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/transaction/ledger?count=true";
        return Async.post(uri, filter, BigDecimal.class);
    }
}
