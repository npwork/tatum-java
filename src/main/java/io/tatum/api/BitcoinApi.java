package io.tatum.api;

import io.tatum.model.response.btc.BtcBlock;
import io.tatum.model.response.btc.BtcInfo;
import io.tatum.model.response.btc.BtcTx;
import io.tatum.model.response.btc.BtcUTXO;
import io.tatum.model.response.common.AddressResponse;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.CreateWalletResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface BitcoinApi {
    // @TODO - broadcast

    /**
     * https://tatum.io/apidoc.php#operation/BtcGenerateWallet
     */
    @GET("v3/bitcoin/wallet")
    Call<CreateWalletResponse> generateWallet();

    /**
     * https://tatum.io/apidoc.php#operation/BtcGenerateAddress
     */
    @GET("v3/bitcoin/address/{xpub}/{index}")
    Call<AddressResponse> addressFromXpub(@Path("xpub") String xpub, @Path("index") int index);

    /**
     * https://tatum.io/apidoc#operation/BtcGetBlockChainInfo
     */
    @GET("v3/bitcoin/info")
    Call<BtcInfo> currentBlock();

    /**
     * https://tatum.io/apidoc#operation/BtcGetBlock
     */
    @GET("v3/bitcoin/block/{blockHash}")
    Call<BtcBlock> getBlock(@Path("blockHash") String blockHash);

    /**
     * https://tatum.io/apidoc#operation/BtcGetBlockHash
     */
    @GET("v3/bitcoin/block/hash/{blockNumber}")
    Call<BlockHash> getBlockHash(@Path("blockNumber") Long blockNumber);

    /**
     * https://tatum.io/apidoc#operation/BtcGetUTXO
     */
    @GET("v3/bitcoin/utxo/{blockHash}/{index}")
    Call<BtcUTXO> getUtxo(@Path("blockHash") String blockHash, @Path("index") int index);

    /**
     * https://tatum.io/apidoc#operation/BtcGetRawTransaction
     */
    @GET("v3/bitcoin/transaction/{txHash}")
    Call<BtcTx> getTransaction(@Path("txHash") String txHash);

    /**
     * https://tatum.io/apidoc#operation/BtcGetTxByAddress
     */
    @GET("v3/bitcoin/transaction/address/{address}")
    Call<List<BtcTx>> getTxForAccount(
            @Path("address") String address,
            @Query("pageSize") int pageSize,
            @Query("offset") int offset
    );

    default Call<List<BtcTx>> getTxForAccount(String address) {
        return getTxForAccount(address, 50, 0);
    }
}
