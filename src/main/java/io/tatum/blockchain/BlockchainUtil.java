package io.tatum.blockchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.utils.Async;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class BlockchainUtil {

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
