package io.tatum.security;

import com.google.common.base.Strings;
import io.tatum.model.request.Currency;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class KMS {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetPendingTransactionToSign" target="_blank">Tatum API documentation</a>
     */
    public TransactionKMS getTransactionKMS(String id) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/kms/" + id;
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new TransactionKMS();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/DeletePendingTransactionToSign" target="_blank">Tatum API documentation</a>
     */
    public void deleteTransactionKMS(String id, Boolean revert) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // revert = true
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/kms/" + id + "/" + revert;
        Async.get(uri, Env.getTatumApiKey());
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/CompletePendingSignature" target="_blank">Tatum API documentation</a>
     */
    public void completePendingTransactionKMS(String id, String txId) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/kms/" + id + "/" + txId;
        String requestBody = "{}";
        Async.put(uri, Env.getTatumApiKey(), requestBody);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetPendingTransactionsToSign" target="_blank">Tatum API documentation</a>
     */
    public TransactionKMS[] getPendingTransactionsKMSByChain(Currency chain) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/kms/pending/" + chain.getCurrency();
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new TransactionKMS[]{};
    }
}
