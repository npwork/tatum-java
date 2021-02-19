package io.tatum.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tatum.model.response.btc.BtcInfo;
import io.tatum.model.response.tron.TronAccount;
import io.tatum.model.response.tron.TronInfo;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class TronTest {
    @Test
    public void tronGetCurrentBlockTest() throws InterruptedException, ExecutionException {
        TronInfo tronInfo = new Tron().TronGetCurrentBlock();
        System.out.println(tronInfo);
//        assertThat(tronInfo, hasProperty("blockNumber"));
//        assertThat(tronInfo, hasProperty("hash"));
    }

    @Test
    public void tronGetAccountTest() throws ExecutionException, InterruptedException, JsonProcessingException {
        TronAccount tronAccount = new Tron().TronGetAccount("TR4KGhjnaysHrw6GiLnL75rNfoBQWZHxm8");
        System.out.println(tronAccount);
    }

    @Test
    public void tronGetFakedAccountTest() throws ExecutionException, InterruptedException, JsonProcessingException {
        String response = "{\n" +
                "  \"address\": \"TGDqQAP5bduoPKVgdbk7fGyW4DwEt3RRn8\",\n" +
                "  \"freeNetUsage\": 1900,\n" +
                "  \"balance\": 2342342,\n" +
                "  \"trc10\": [\n" +
                "    {\n" +
                "      \"key\": \"TEST_TRC_10\",\n" +
                "      \"value\": 123\n" +
                "    }\n" +
                "  ],\n" +
                "  \"trc20\": [\n" +
                "    {\n" +
                "      \"TRkuKAxmWZ4G74MvZnFpoosQZsfvtNpmwH\": \"30958\",\n" +
                "      \"TVKXY8DJ9aVTcg3wwVrRRs2FbTUwi4UZWR\": \"70000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"createTime\": 1602848895000,\n" +
                "  \"assetIssuedId\": \"1003475\",\n" +
                "  \"assetIssuedName\": 100\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        TronAccount output = objectMapper.readValue(response, TronAccount.class);
        System.out.println(output);

        String result = objectMapper.writeValueAsString(output);
        System.out.println(result);
    }
}
