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

/**
 * The type Xrp.
 */
public class XRP {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpBroadcast" target="_blank">Tatum API documentation</a>
     *
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public TransactionHash xrpBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetFee" target="_blank">Tatum API documentation</a>
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     *
     * @param address the address
     * @return the account data
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
            accountData.setAccount(jsonObject.getString("Account"));
            return accountData;
        }
        return Async.get(uri, AccountData.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     *
     * @param ledgerIndex the ledger index
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String xrpGetLedger(BigDecimal ledgerIndex) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/ledger/" + ledgerIndex;
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountBalance" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
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
     *
     * @param hash the hash
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String xrpGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/transaction/" + hash;
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountTx" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @param min     the min
     * @param marker  the marker
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String xrpGetAccountTransactions(String address, BigDecimal min, String marker) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/tx/" + address + "?min=" + min + "&marker=" + URLEncoder.encode(marker, StandardCharsets.UTF_8);
        System.out.println(uri);
        return Async.get(uri);
    }

}
