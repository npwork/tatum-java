package io.tatum.offchain;

import io.tatum.model.request.TransferBtcBasedOffchainKMS;
import io.tatum.model.request.TransferEthErc20OffchainKMS;
import io.tatum.model.request.TransferEthOffchainKMS;
import io.tatum.model.response.common.SignatureId;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class KMSOffchain {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BtcTransfer" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferBtcKMS(TransferBtcBasedOffchainKMS body) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/bitcoin/transfer";
        return Async.post(uri, body, SignatureId.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/LtcTransfer" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferLtcKMS(TransferBtcBasedOffchainKMS body) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/litecoin/transfer";
        return Async.post(uri, body, SignatureId.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/BchTransfer" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferBcashKMS(TransferBtcBasedOffchainKMS body) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/bcash/transfer";
        return Async.post(uri, body, SignatureId.class);

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthTransfer" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferEthKMS(TransferEthOffchainKMS body) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/ethereum/transfer";
        return Async.post(uri, body, SignatureId.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/EthTransferErc20" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferEthErc20KMS(TransferEthErc20OffchainKMS body) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/ethereum/erc20/transfer";
        return Async.post(uri, body, SignatureId.class);

    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XlmTransfer" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferXlmKMS(TransferBtcBasedOffchainKMS body) throws InterruptedException, ExecutionException, IOException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/xlm/transfer";
        return Async.post(uri, body, SignatureId.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/XrpTransfer" target="_blank">Tatum API documentation</a>
     */
    public SignatureId offchainTransferXrpKMS(TransferBtcBasedOffchainKMS body) throws InterruptedException, ExecutionException, IOException {

        String uri = BaseUrl.getInstance().getUrl() + "/v3/offchain/xlm/transfer";
        return Async.post(uri, body, SignatureId.class);
    }

}
