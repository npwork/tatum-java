package io.tatum.api;

import io.tatum.model.response.xlm.XlmAccount;
import io.tatum.model.response.xlm.XlmInfo;
import io.tatum.model.response.xlm.XlmLedger;
import io.tatum.model.response.xlm.XlmTransaction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface XlmApi {
    /**
     * https://tatum.io/apidoc#operation/XlmGetAccountInfo
     */
    @GET("v3/xlm/account/{account}")
    Call<XlmAccount> getAccount(@Path("account") String account);

    /**
     * https://tatum.io/apidoc#operation/XlmGetLastClosedLedger
     */
    @GET("v3/xlm/info")
    Call<XlmInfo> info();

    /**
     * https://tatum.io/apidoc#operation/XlmGetFee
     */
    @GET("v3/xlm/fee")
    Call<Long> feeInfo();

    /**
     * https://tatum.io/apidoc#operation/XlmGetLedger
     */
    @GET("v3/xlm/ledger/{sequence}")
    Call<XlmLedger> getLedger(@Path("sequence") Long sequence);

    /**
     * https://tatum.io/apidoc#operation/XlmGetLedgerTx
     */
    @GET("v3/xlm/ledger/{sequence}/transaction")
    Call<List<XlmTransaction>> getLedgerTx(@Path("sequence") Long sequence);

    /**
     * https://tatum.io/apidoc#operation/XlmGetTransaction
     */
    @GET("v3/xlm/transaction/{txHash}")
    Call<XlmTransaction> getTransaction(@Path("txHash") String txHash);

    /**
     * https://tatum.io/apidoc#operation/XlmGetAccountTx
     */
    @GET("v3/xlm/account/tx/{account}")
    Call<List<XlmTransaction>> getAccountTx(@Path("account") String account);
}
