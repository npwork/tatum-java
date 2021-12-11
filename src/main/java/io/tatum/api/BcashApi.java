package io.tatum.api;

import io.tatum.model.response.bch.BchBlock;
import io.tatum.model.response.bch.BchInfo;
import io.tatum.model.response.bch.BchTx;
import io.tatum.model.response.common.BlockHash;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface BcashApi {
    /**
     * https://tatum.io/apidoc#operation/BchGetBlockChainInfo
     */
    @GET("v3/bcash/info")
    Call<BchInfo> info();

    /**
     * https://tatum.io/apidoc#operation/BchGetBlock
     */
    @GET("v3/bcash/block/{blockHash}")
    Call<BchBlock> getBlock(@Path("blockHash") String blockHash);

    /**
     * https://tatum.io/apidoc#operation/BchGetBlockHash
     */
    @GET("v3/bcash/block/hash/{blockNumber}")
    Call<BlockHash> getBlockHash(@Path("blockNumber") Long blockNumber);

    /**
     * https://tatum.io/apidoc#operation/BchGetTxByAddress
     */
    @GET("v3/bcash/transaction/address/{address}")
    Call<List<BchTx>> getTxForAccount(@Path("address") String address);

    /**
     * https://tatum.io/apidoc#operation/BchGetRawTransaction
     */
    @GET("v3/bcash/transaction/{txHash}")
    Call<BchTx> getTransaction(@Path("txHash") String txHash);
}
