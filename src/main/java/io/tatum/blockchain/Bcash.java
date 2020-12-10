package io.tatum.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.response.bch.*;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.IBlockHash;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.utils.Async;
import io.tatum.utils.Env;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public final class Bcash {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash bcashBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bcash/broadcast";

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
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetBlockChainInfo" target="_blank">Tatum API documentation</a>
     */
    public IBchInfo bcashGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bcash/info";
        String block = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BchInfo();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetBlock" target="_blank">Tatum API documentation</a>
     */
    public IBchBlock bcashGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bcash/block/" + hash;
        String block = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BchBlock();
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetBlockHash" target="_blank">Tatum API documentation</a>
     */
    public IBlockHash bcashGetBlockHash(BigDecimal i) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bcash/block/hash/" + i;
        String hash = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BlockHash();

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetTxByAddress" target="_blank">Tatum API documentation</a>
     */
    public IBchTx[] bcashGetTxForAccount(String address, BigDecimal skip) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bcash/transaction/address/" + address + "?skip=" + skip;
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new IBchTx[]{};
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchGetRawTransaction" target="_blank">Tatum API documentation</a>
     */
    public IBchTx bcashGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String tatumApiUrl = Env.getTatumApiUrl();
        String uri = (Strings.isNullOrEmpty(tatumApiUrl) ? TATUM_API_URL : tatumApiUrl) + "/v3/bcash/transaction/" + hash;
        String tx = Async.get(uri, Env.getTatumApiKey());
        // TO-DO
        return new BchTx();
    }

}
