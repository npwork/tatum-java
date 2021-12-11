package io.tatum.api;

import io.tatum.model.response.tron.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TronApi {
    /**
     * https://tatum.io/apidoc#operation/TronGetCurrentBlock
     */
    @GET("v3/tron/info")
    Call<TronInfo> info();

    /**
     * https://tatum.io/apidoc#operation/TronGetBlock
     */
    @GET("v3/tron/block/{blockHash}")
    Call<TronBlock> getBlock(@Path("blockHash") String blockHash);

    /**
     * https://tatum.io/apidoc#operation/TronTrc10Detail
     */
    @GET("v3/tron/trc10/detail/{id}")
    Call<TronTrc10> getTrc10Details(@Path("id") long id);

    /**
     * https://tatum.io/apidoc#operation/TronGetTransaction
     */
    @GET("v3/tron/transaction/{txHash}")
    Call<TronTransaction> getTransaction(@Path("txHash") String txHash);

    /**
     * https://tatum.io/apidoc#operation/TronGetTransaction
     */
    @GET("v3/tron/account/{address}")
    Call<TronAccount> getAccount(@Path("address") String address);
}
