package io.tatum.api;

import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.ltc.LtcBlock;
import io.tatum.model.response.ltc.LtcInfo;
import io.tatum.model.response.ltc.LtcTx;
import io.tatum.model.response.ltc.LtcUTXO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface LitecoinApi {
    /**
     * https://tatum.io/apidoc#operation/LtcGetBlockChainInfo
     */
    @GET("v3/litecoin/info")
    Call<LtcInfo> info();

    /**
     * https://tatum.io/apidoc#operation/LtcGetBlock
     */
    @GET("v3/litecoin/block/{blockHash}")
    Call<LtcBlock> getBlock(@Path("blockHash") String blockHash);

    /**
     * https://tatum.io/apidoc#operation/LtcGetBlockHash
     */
    @GET("v3/litecoin/block/hash/{blockNumber}")
    Call<BlockHash> getBlockHash(@Path("blockNumber") Long blockNumber);

    /**
     * https://tatum.io/apidoc#operation/LtcGetUTXO
     */
    @GET("v3/litecoin/utxo/{txHash}/{index}")
    Call<LtcUTXO> getUtxo(@Path("txHash") String txHash, @Path("index") int index);

    /**
     * https://tatum.io/apidoc#operation/LtcGetTxByAddress
     */
    @GET("v3/litecoin/transaction/address/{address}")
    Call<List<LtcTx>> getTxForAccount(
            @Path("address") String address,
            @Query("pageSize") int pageSize,
            @Query("offset") int offset
    );

    default Call<List<LtcTx>> getTxForAccount(String address) {
        return getTxForAccount(address, 50, 0);
    }

    /**
     * https://tatum.io/apidoc#operation/LtcGetRawTransaction
     */
    @GET("v3/litecoin/transaction/{txHash}")
    Call<LtcTx> getTransaction(@Path("txHash") String address);
}
