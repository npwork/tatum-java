package io.tatum.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.tatum.model.request.EstimateGasVet;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.ltc.LtcInfo;
import io.tatum.model.response.vet.VetBlock;
import io.tatum.model.response.vet.VetEstimateGas;
import io.tatum.model.response.vet.VetTx;
import io.tatum.model.response.vet.VetTxReceipt;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.Env;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

public class VET {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash vetBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/broadcast";
        TransactionHash hash = BlockchainUtil.broadcast(uri, txData, signatureId);
        return hash;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetEstimateGas" target="_blank">Tatum API documentation</a>
     */
    public VetEstimateGas vetEstimateGas(EstimateGasVet body) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(body);
        String uri = BaseUrl.getInstance().url + "/v3/vet/broadcast/transaction/gas";
        return Async.get(uri, ApiKey.getInstance().apiKey, VetEstimateGas.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetCurrentBlock" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal vetGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/current";
        var res = Async.get(uri, ApiKey.getInstance().apiKey);

        var block = BigDecimal.ZERO;
        if (res.statusCode() == 200) {
            var objectMapper = new ObjectMapper();
            var value =  objectMapper.writeValueAsString(res.body());
            block = new BigDecimal(value);
        }

        return block;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetBlock" target="_blank">Tatum API documentation</a>
     */
    public VetBlock vetGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/block/" + hash;
        return Async.get(uri, ApiKey.getInstance().apiKey, VetBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal vetGetAccountBalance(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/account/balance/" + address;
        var res = Async.get(uri, ApiKey.getInstance().apiKey);

        var balance = BigDecimal.ZERO;
        if (res.statusCode() == 200) {
            var objectMapper = new ObjectMapper();
            var json = objectMapper.writeValueAsString(res.body());
            JSONObject jsonObject = new JSONObject(json);
            balance = new BigDecimal(jsonObject.getString("balance"));
        }

        return balance;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetEnergy" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal vetGetAccountEnergy(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/account/energy/" + address;
        var energy = Async.get(uri, ApiKey.getInstance().apiKey);
        // TO-DO
        return new BigDecimal(0);

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public VetTx vetGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/transaction/" + hash;
        return Async.get(uri, ApiKey.getInstance().apiKey, VetTx.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetTransactionReceipt" target="_blank">Tatum API documentation</a>
     */
    public VetTxReceipt vetGetTransactionReceipt(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().url + "/v3/vet/transaction/" + hash + "/receipt";
        return Async.get(uri, ApiKey.getInstance().apiKey, VetTxReceipt.class);
    }

}
