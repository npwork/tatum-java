package io.tatum.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.utils.Async;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

/**
 * The type Blockchain util.
 */
public class BlockchainUtil {

    /**
     * Broadcast transaction hash.
     *
     * @param uri         the uri
     * @param txData      the tx data
     * @param signatureId the signature id
     * @return the transaction hash
     * @throws IOException          the io exception
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public static TransactionHash broadcast (String uri, String txData, String signatureId) throws IOException, ExecutionException, InterruptedException {
        var values = new HashMap<String, String>() {{
            put("txData", txData);
        }};
        if (signatureId != null) {
            values.put("signatureId", signatureId);
        }

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);
        return Async.post(uri, requestBody, TransactionHash.class);
    }

}
