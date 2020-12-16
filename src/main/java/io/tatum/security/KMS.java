package io.tatum.security;

import io.tatum.model.request.Currency;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.EMPTY_BODY;

public class KMS {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetPendingTransactionToSign" target="_blank">Tatum API documentation</a>
     */
    public TransactionKMS getTransactionKMS(String id) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/kms/" + id;
        return Async.get(uri, TransactionKMS.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/DeletePendingTransactionToSign" target="_blank">Tatum API documentation</a>
     */
    public void deleteTransactionKMS(String id, Boolean revert) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/kms/" + id + "/?revert=" + revert;
        Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/CompletePendingSignature" target="_blank">Tatum API documentation</a>
     */
    public void completePendingTransactionKMS(String id, String txId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/kms/" + id + "/" + txId;
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/GetPendingTransactionsToSign" target="_blank">Tatum API documentation</a>
     */
    public TransactionKMS[] getPendingTransactionsKMSByChain(Currency chain) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/kms/pending/" + chain.getCurrency();
        return Async.get(uri, TransactionKMS[].class);
    }
}
