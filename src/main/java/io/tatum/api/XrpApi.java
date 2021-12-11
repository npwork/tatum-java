package io.tatum.api;

import io.tatum.model.response.xrp.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XrpApi {
    /**
     * https://tatum.io/apidoc.php#operation/XrpGetLastClosedLedger
     */
    @GET("v3/xrp/info")
    Call<XrpInfo> info();

    /**
     * https://tatum.io/apidoc#operation/XrpGetFee
     */
    @GET("v3/xrp/fee")
    Call<XrpFeeInfo> feeInfo();

    /**
     * https://tatum.io/apidoc#operation/XrpGetAccountInfo
     */
    @GET("v3/xrp/account/{account}")
    Call<AccountData> getAccount(@Path("account") String account);

    /**
     * https://tatum.io/apidoc#operation/XrpGetLedger
     */
    @GET("v3/xrp/ledger/{ledgerIndex}")
    Call<XrpLedger> getLedger(@Path("ledgerIndex") long ledgerIndex);

    /**
     * https://tatum.io/apidoc#operation/XrpGetAccountBalance
     *
     * @TODO bug in tatum. If account not found - returns null with 403 status
     */
    @GET("v3/xrp/account/{account}/balance")
    Call<XrpAccountBalance> getBalance(@Path("account") String account);

    /**
     * https://tatum.io/apidoc#operation/XrpGetTransaction
     */
    @GET("v3/xrp/transaction/{txHash}")
    Call<XrpTransaction> getTransaction(@Path("txHash") String txHash);

    /**
     * https://tatum.io/apidoc#operation/XrpGetAccountTx
     */
    @GET("v3/xrp/account/tx/{account}")
    Call<XrpAccountTransactions> getAccountTx(@Path("account") String account);
}
