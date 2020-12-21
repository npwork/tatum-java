package io.tatum.constants;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

public class Constant {
    public static final String TATUM_API_URL = "https://api-eu1.tatum.io";

    public static final String EMPTY_BODY = "{}";

    public static final NetworkParameters BITCOIN_MAINNET = MainNetParams.get();

    public static final NetworkParameters BITCOIN_TESTNET = TestNet3Params.get();

}
