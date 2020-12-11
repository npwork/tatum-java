package io.tatum.ledger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.request.CreateTransaction;
import io.tatum.model.request.TransactionFilter;
import io.tatum.model.response.ledger.Transaction;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class LedgerTransaction {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByReference" target="_blank">Tatum API documentation</a>
     */
    public Transaction[] getTransactionsByReference(String reference) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/reference/" + reference;
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new Transaction[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/sendTransaction" target="_blank">Tatum API documentation</a>
     */
    public String storeTransaction(CreateTransaction transaction) throws IOException, ExecutionException, InterruptedException {
//        TO-DO
//        await validateOrReject(transaction);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction";
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(transaction);

        String ref = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO reference: string
        return ref;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByAccountId" target="_blank">Tatum API documentation</a>
     */
    public Transaction[] getTransactionsByAccount(TransactionFilter filter, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(filter);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/account?pageSize=" + pageSize + "&offset=" + offset;
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filter);

        String tx = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return new Transaction[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByCustomerId" target="_blank">Tatum API documentation</a>
     */
    public Transaction[] getTransactionsByCustomer(TransactionFilter filter, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(filter);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/customer?pageSize=" + pageSize + "&offset=" + offset;
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filter);

        String tx = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return new Transaction[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactions" target="_blank">Tatum API documentation</a>
     */
    public Transaction[] getTransactionsByLedger(TransactionFilter filter, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(filter);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/ledger?pageSize=" + pageSize + "&offset=" + offset;
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filter);

        String tx = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return new Transaction[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByAccountId" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal countTransactionsByAccount(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(filter);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/account?count=true";
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filter);

        String count = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return new BigDecimal(count);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactionsByCustomerId" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal countTransactionsByCustomer(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(filter);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/customer?count=true";
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filter);

        String count = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return new BigDecimal(count);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getTransactions" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal countTransactionsByLedger(TransactionFilter filter) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(filter);
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/ledger/transaction/ledger?count=true";
        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(filter);

        String count = Async.post(uri, Env.getTatumApiKey(), requestBody);
        // TO-DO
        return new BigDecimal(count);
    }
}
