package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xrp.Account;
import io.tatum.model.response.xrp.IAccount;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class XRP {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash xrpBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/broadcast";
        TransactionHash hash = BlockchainUtil.broadcast(uri, txData, signatureId);
        return hash;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetFee" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetFee() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/fee";
        var base_fee = Async.get(uri, ApiKey.getInstance().apiKey);
        return new BigDecimal(0);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountInfo" target="_blank">Tatum API documentation</a>
     */
    public IAccount xrpGetAccountInfo(String account) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/account/" + account;
        var acc = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return new Account();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetCurrentLedger() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/info";
        var current = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return new BigDecimal(0);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLedger" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetLedger(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/ledger/" + i;
        var ledger = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "ledger";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountBalance" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetAccountBalance(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/account/" + address + "/balance";
        var balance = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/transaction/" + hash;
        var tx = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "tx";
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountTx" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetAccountTransactions(String address, BigDecimal min, String marker) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/xrp/account/tx" + address + "?min=" + min + "&marker=" + marker;
        var tx = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return "tx";
    }

}
