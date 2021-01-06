package io.tatum.transaction.eth;

import io.tatum.utils.ApiKey;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;

/**
 * The type Web 3 j client.
 */
@Log4j2
public class Web3jClient {

    private Web3jClient() {
    }

    /**
     * Get web 3 j.
     *
     * @param provider the provider
     * @return the web 3 j
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public static Web3j get(String provider) throws ExecutionException, InterruptedException {
        var url = StringUtils.isNotEmpty(provider) ? provider : TATUM_API_URL + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
        Web3j web3j = Web3j.build(new HttpService(url));
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        log.info(clientVersion);
        return web3j;
    }
}
