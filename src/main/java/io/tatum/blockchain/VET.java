package io.tatum.blockchain;

import com.google.common.base.Preconditions;
import io.tatum.model.request.EstimateGasVet;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.common.Balance;
import io.tatum.model.response.vet.*;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.ObjectValidator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

/**
 * The type Vet.
 */

public class VET {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetBroadcast" target="_blank">Tatum API documentation</a>
     *
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public TransactionHash vetBroadcast(String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/broadcast";
        return BlockchainUtil.broadcast(uri, txData, signatureId);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetEstimateGas" target="_blank">Tatum API documentation</a>
     *
     * @param body the body
     * @return the vet estimate gas
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    public VetEstimateGas vetEstimateGas(EstimateGasVet body) throws ExecutionException, InterruptedException, IOException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/transaction/gas";
        return Async.post(uri, body, VetEstimateGas.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetCurrentBlock" target="_blank">Tatum API documentation</a>
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public Long vetGetCurrentBlock() throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/block/current";
        return Async.get(uri, Long.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetBlock" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the vet block
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public VetBlock vetGetBlock(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/block/" + hash;
        return Async.get(uri, VetBlock.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetBalance" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal vetGetAccountBalance(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/account/balance/" + address;
        Balance res = Async.get(uri, Balance.class);
        if (res != null) {
            return res.getBalance();
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetEnergy" target="_blank">Tatum API documentation</a>
     *
     * @param address the address
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public BigDecimal vetGetAccountEnergy(String address) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/account/energy/" + address;
        var energy = Async.get(uri, Energy.class);
        if (energy != null) {
            return energy.getEnergy();
        }
        return null;

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetTransaction" target="_blank">Tatum API documentation</a>
     *
     * @param hash the hash
     * @return the vet tx
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public VetTx vetGetTransaction(String hash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/transaction/" + hash;
        return Async.get(uri, VetTx.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/VetGetTransactionReceipt" target="_blank">Tatum API documentation</a>
     *
     * @param txHash the tx hash
     * @return the vet tx receipt
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public VetTxReceipt vetGetTransactionReceipt(String txHash) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/vet/transaction/" + txHash + "/receipt";
        return Async.get(uri, VetTxReceipt.class);
    }

}
