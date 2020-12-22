package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xrp.AccountData;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    public BigDecimal xrpGetFee() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/fee";
        var res = Async.get(uri);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            JSONObject drops = jsonObject.getJSONObject("drops");
            return drops.getBigDecimal("base_fee");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountInfo" target="_blank">Tatum API documentation</a>
     */
    public AccountData xrpGetAccountInfo(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/" + address;
        var res = Async.get(uri);
        if (res != null) {
            AccountData accountData = new AccountData();
            JSONObject jsonObject = new JSONObject(res);
            JSONObject account_data = jsonObject.getJSONObject("account_data");
            accountData.setSequence(account_data.getInt("Sequence"));
            accountData.setLedgerCurrentIndex(jsonObject.getInt("ledger_current_index"));
            return accountData;
        }
        return Async.get(uri, AccountData.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetCurrentLedger() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/info";
        var res = Async.get(uri);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getBigDecimal("ledger_index");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLedger" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetLedger(BigDecimal ledgerIndex) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/ledger/" + ledgerIndex;
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xrpGetAccountBalance(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/" + address + "/balance";
        var res = Async.get(uri);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getBigDecimal("balance");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/transaction/" + hash;
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountTx" target="_blank">Tatum API documentation</a>
     */
    public String xrpGetAccountTransactions(String address, BigDecimal min, String marker) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/tx/" + address + "?min=" + min + "&marker=" + URLEncoder.encode(marker, StandardCharsets.UTF_8);
        System.out.println(uri);
        return Async.get(uri);
    }

}
