package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xlm.Account;
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
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetAccountInfo" target="_blank">Tatum API documentation</a>
     */
    public Account xlmGetAccountInfo(String account) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/account/" + account;
        return Async.get(uri, Account.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetCurrentLedger() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/info";
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetFee" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal xlmGetFee() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/fee";
        return Async.get(uri, BigDecimal.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLedger" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetLedger(BigDecimal sequence) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/ledger/" + sequence;
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLedgerTx" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetLedgerTx(BigDecimal sequence) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/ledger/" + sequence + "/transaction";
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/transaction/" + hash;
        return Async.get(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetAccountTx" target="_blank">Tatum API documentation</a>
     */
    public String xlmGetAccountTransactions(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/account/tx/" + address;
        return Async.get(uri);
    }
}
