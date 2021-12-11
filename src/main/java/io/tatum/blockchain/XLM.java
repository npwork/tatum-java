package io.tatum.blockchain;

import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.xlm.XlmAccount;
import io.tatum.model.response.xlm.XlmInfo;
import io.tatum.model.response.xlm.XlmLedger;
import io.tatum.model.response.xlm.XlmTransaction;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The type Xlm.
 */
public class XLM {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmBroadcast" target="_blank">Tatum API documentation</a>
     *
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public TransactionHash xlmBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetAccountInfo" target="_blank">Tatum API documentation</a>
     *
     * @param account the account
     * @return the account
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public XlmAccount xlmGetAccountInfo(String account) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/account/" + account;
        return Async.get(uri, XlmAccount.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLastClosedLedger" target="_blank">Tatum API documentation</a>
     *
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public XlmInfo xlmGetCurrentLedger() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/info";
        return Async.get(uri, XlmInfo.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetFee" target="_blank">Tatum API documentation</a>
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public long xlmGetFee() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/fee";
        return Async.get(uri, Long.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLedger" target="_blank">Tatum API documentation</a>
     *
     * @param sequence the sequence
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public XlmLedger xlmGetLedger(long sequence) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/ledger/" + sequence;
        return Async.get(uri, XlmLedger.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetLedgerTx" target="_blank">Tatum API documentation</a>
     *
     * @param sequence the sequence
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public XlmTransaction[] xlmGetLedgerTx(long sequence) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/ledger/" + sequence + "/transaction";
        // new TypeReference<List<MyClass>>(){}
        return Async.get(uri, XlmTransaction[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetTransaction" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public XlmTransaction xlmGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/transaction/" + hash;
        return Async.get(uri, XlmTransaction.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmGetAccountTx" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public XlmTransaction[] xlmGetAccountTransactions(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/xlm/account/tx/" + address;
        return Async.get(uri, XlmTransaction[].class);
    }
}
