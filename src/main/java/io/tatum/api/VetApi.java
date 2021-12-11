package io.tatum.api;

import io.tatum.model.request.EstimateGasVet;
import io.tatum.model.response.common.Balance;
import io.tatum.model.response.vet.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VetApi {
    /**
     * https://tatum.io/apidoc#operation/VetEstimateGas
     */
    @GET("v3/vet/transaction/gas")
    Call<VetEstimateGas> estimateGas(@Body EstimateGasVet body);


    /**
     * https://tatum.io/apidoc#operation/VetGetCurrentBlock
     */
    @GET("v3/vet/block/current")
    Call<Long> currentBlock();

    /**
     * https://tatum.io/apidoc#operation/VetGetBlock
     */
    @GET("v3/vet/block/{blockHash}")
    Call<VetBlock> getBlock(@Path("blockHash") String blockHash);

    /**
     * https://tatum.io/apidoc#operation/VetGetBalance
     */
    @GET("v3/vet/account/balance/{account}")
    Call<Balance> getBalance(@Path("account") String account);

    /**
     * https://tatum.io/apidoc#operation/VetGetEnergy
     */
    @GET("v3/vet/account/energy/{account}")
    Call<Energy> getAccountEnergy(@Path("account") String account);

    /**
     * https://tatum.io/apidoc#operation/VetGetTransaction
     */
    @GET("v3/vet/transaction/{txHash}")
    Call<VetTx> getTransaction(@Path("txHash") String txHash);

    /**
     * https://tatum.io/apidoc#operation/VetGetTransactionReceipt
     */
    @GET("v3/vet/transaction/{txHash}/receipt")
    Call<VetTxReceipt> getTransactionReceipt(@Path("txHash") String txHash);
}
