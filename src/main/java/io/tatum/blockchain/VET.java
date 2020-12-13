package io.tatum.blockchain;

import io.tatum.model.request.EstimateGasVet;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.eth.Balance;
import io.tatum.model.response.vet.*;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public class VET {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetBroadcast" target="_blank">Tatum API documentation</a>
     */
    public TransactionHash vetBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/broadcast";
        TransactionHash hash = BlockchainUtil.broadcast(uri, txData, signatureId);
        return hash;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetEstimateGas" target="_blank">Tatum API documentation</a>
     */
    public VetEstimateGas vetEstimateGas(EstimateGasVet body) throws IOException, ExecutionException, InterruptedException {
        // TO-DO
        // await validateOrReject(body);
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/broadcast/transaction/gas";
        return Async.get(uri, VetEstimateGas.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetCurrentBlock" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal vetGetCurrentBlock() throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/current";
        var res = Async.get(uri, String.class);
        if (res != null) {
            return new BigDecimal(res);
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetBlock" target="_blank">Tatum API documentation</a>
     */
    public VetBlock vetGetBlock(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/block/" + hash;
        return Async.get(uri, VetBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetBalance" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal vetGetAccountBalance(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/account/balance/" + address;
        Balance res = Async.get(uri, Balance.class);
        if (res != null) {
            return res.getBalance();
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetEnergy" target="_blank">Tatum API documentation</a>
     */
    public BigDecimal vetGetAccountEnergy(String address) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/account/energy/" + address;
        var energy = Async.get(uri, Energy.class);
        if (energy != null) {
            return energy.getEnergy();
        }
        return null;

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetTransaction" target="_blank">Tatum API documentation</a>
     */
    public VetTx vetGetTransaction(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/transaction/" + hash;
        return Async.get(uri, VetTx.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetTransactionReceipt" target="_blank">Tatum API documentation</a>
     */
    public VetTxReceipt vetGetTransactionReceipt(String hash) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/transaction/" + hash + "/receipt";
        return Async.get(uri, VetTxReceipt.class);
    }

}
