package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xrp.Account;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class XRP {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash xrpBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetFee" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetFee() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/fee";
        var res = Async.get(uri, String.class);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            String drops = jsonObject.getString("drops");
            return new JSONObject(drops).getBigDecimal("base_fee");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountInfo" target="_blank">Tatum API documentation</a>
     */
    public Account xrpGetAccountInfo(String account) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/" + account;
        return Async.get(uri, Account.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetCurrentLedger() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/info";
        var res = Async.get(uri, String.class);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getBigDecimal("ledger_index");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLedger" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetLedger(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/ledger/" + i;
        var res = Async.get(uri, String.class);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getBigDecimal("ledger_index");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetAccountBalance(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/" + address + "/balance";
        var res = Async.get(uri, String.class);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getBigDecimal("balance");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/transaction/" + hash;
        return Async.get(uri, String.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountTx" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetAccountTransactions(String address, BigDecimal min, String marker) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/tx" + address + "?min=" + min + "&marker=" + marker;
        return Async.get(uri, String.class);
    }

}
