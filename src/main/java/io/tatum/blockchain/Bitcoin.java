package io.tatum.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.response.btc.*;
import io.tatum.model.response.common.*;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public final class Bitcoin {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash btcBroadcast(final String txData, final String signatureId) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/broadcast";

        var values = new HashMap<String, String>() {{
            put("txData", txData);
            put("signatureId", signatureId);
        }};

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        String txId = Async.post(uri, Env.getTatumApiKey(), requestBody);
        return new TransactionHash(txId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     */
    public IChainInfo btcGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/info";
        String block = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BtcInfo();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetBlock" target="_blank">Tatum API documentation</a>
     */
    public IBtcBlock btcGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/block/" + hash;
        String block = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BtcBlock();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetBlockHash" target="_blank">Tatum API documentation</a>
     */
    public IBlockHash btcGetBlockHash(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/block/hash/" + i;
        String hash = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BlockHash();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetUTXO" target="_blank">Tatum API documentation</a>
     */
    public IUTXO btcGetUTXO(String hash, BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/utxo/" + hash + "/" + i;
        String utxo = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BtcUTXO();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetTxByAddress" target="_blank">Tatum API documentation</a>
     */
    public IBtcTx[] btcGetTxForAccount(String address, Integer pageSize, Integer offset) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/transaction/address/" + address + "?pageSize=" + pageSize + "&offset=" + offset;
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BtcTx[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcGetRawTransaction" target="_blank">Tatum API documentation</a>
     */
    public IBtcTx btcGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bitcoin/transaction/" + hash;
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BtcTx();
    }
}
