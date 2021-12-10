package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xrp.AccountData;
import io.tatum.model.response.xrp.XrpAccountTransactions;
import io.tatum.model.response.xrp.XrpLedger;
import io.tatum.model.response.xrp.XrpTransaction;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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
        return Async.get(uri, AccountData.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Long xrpGetCurrentLedger() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/info";
        var res = Async.get(uri);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getLong("ledger_index");
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
    public XrpLedger xrpGetLedger(Long ledgerIndex) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/ledger/" + ledgerIndex;
        return Async.get(uri, XrpLedger.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpGetAccountBalance" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigInteger xrpGetAccountBalance(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/" + address + "/balance";
        var res = Async.get(uri);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getBigInteger("balance");
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
    public XrpTransaction xrpGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/transaction/" + hash;
        return Async.get(uri, XrpTransaction.class);
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
    public String xrpGetAccountTransactions(String address, BigInteger min, String marker) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/tx/" + address + "?min=" + min + "&marker=" + URLEncoder.encode(marker, StandardCharsets.UTF_8);
        return Async.get(uri);
    }

    public XrpAccountTransactions xrpGetAccountTransactions(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xrp/account/tx/" + address;
        return Async.get(uri, XrpAccountTransactions.class);
    }

}
