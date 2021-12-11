package io.tatum.api;

import io.tatum.model.response.common.Balance;
import io.tatum.model.response.eth.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface EthereumApi {
    /**
     * https://tatum.io/apidoc#operation/EthBroadcast
     *//*
    @GET("v3/ethereum/broadcast")
    Call<TransactionHash> generateWallet();*/

    /**
     * https://tatum.io/apidoc#operation/EthGetTransactionCount
     */
    @GET("v3/ethereum/web3/{api_key}")
    Call<EthWeb3Response> web3(@Path("api_key") String apiKey, @Body EthWeb3Request body);

    /**
     * https://tatum.io/apidoc#operation/EthGetTransactionCount
     */
    @GET("v3/ethereum/transaction/count/{address}")
    Call<Long> transactionCount(@Path("address") String address);

    /**
     * https://tatum.io/apidoc#operation/EthGetCurrentBlock
     */
    @GET("v3/ethereum/block/current")
    Call<Long> currentBlock();

    /**
     * https://tatum.io/apidoc#operation/EthGetBlock
     */
    @GET("v3/ethereum/block/{blockHash}")
    Call<EthBlock> getBlock(@Path("blockHash") String blockHash);

    /**
     * https://tatum.io/apidoc#operation/EthGetBalance
     */
    @GET("v3/ethereum/account/balance/{address}")
    Call<Balance> getAccountBalance(@Path("address") String address);

    /**
     * https://tatum.io/apidoc#operation/EthErc20GetBalance
     */
    @GET("v3/ethereum/account/balance/erc20/{address}")
    Call<Balance> getAccountBalance(@Path("address") String address, @Query("contractAddress") String contractAddress);

    /**
     * https://tatum.io/apidoc#operation/EthGetTransaction
     */
    @GET("v3/ethereum/transaction/{txHash}")
    Call<EthTx> getTransaction(@Path("txHash") String txHash);

    /**
     * https://tatum.io/apidoc#operation/EthGetTransactionByAddress
     */
    @GET("v3/ethereum/account/transaction/{address}")
    Call<List<EthTx>> getTransactionsByAddress(
            @Path("address") String address,
            @Query("pageSize") int pageSize,
            @Query("offset") int offset
    );

    default Call<List<EthTx>> getTransactionsByAddress(String address) {
        return getTransactionsByAddress(address, 50, 0);
    }
}
