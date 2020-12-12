package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xlm.Account;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class XLM {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash xlmBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/broadcast";
        TransactionHash hash = BlockchainUtil.broadcast(uri, txData, signatureId);
        return hash;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetAccountInfo" target="_blank">Tatum API documentation</a>
     */
    public Account xlmGetAccountInfo(String account) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/account/" + account;
        return Async.get(uri, ApiKey.getInstance().apiKey, Account.class);

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetCurrentLedger() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/info";
        var info = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "info";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetFee" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetFee() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/fee";
        var fee = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "fee";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLedger" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetLedger(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/ledger/" + i;
        var ledger = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "ledger";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLedgerTx" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetLedgerTx(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/ledger/" + i + "/transaction";
        var tx = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "tx";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/transaction/" + hash;
        var tx = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "tx";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetAccountTx" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetAccountTransactions(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xlm/account/tx/" + address;
        var tx = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "tx";
    }
}
