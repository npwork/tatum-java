package io.tatum.constants;

import io.tatum.utils.LtcMainNetParams;
import io.tatum.utils.LtcTestNet3Params;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

public class Constant {
    public static final String TATUM_API_URL = "https://api-eu1.tatum.io";

    public static final String EMPTY_BODY = "{}";

    public static final NetworkParameters BITCOIN_MAINNET = MainNetParams.get();

    public static final NetworkParameters BITCOIN_TESTNET = TestNet3Params.get();

    public static final NetworkParameters LITECOIN_MAINNET = LtcMainNetParams.get();

    public static final NetworkParameters LITECOIN_TESTNET = LtcTestNet3Params.get();


    // TESTNET_DERIVATION_PATH = 'm/44\'/1\'/0\'/0'
    public static final String TESTNET_DERIVATION_PATH = "M/44H/1H/0H/0";

    // BTC_DERIVATION_PATH = 'm/44\'/0\'/0\'/0';
    public static final String BTC_DERIVATION_PATH = "M/44H/0H/0H/0";

    //LTC_DERIVATION_PATH = 'm/44\'/2\'/0\'/0';
    public static final String LTC_DERIVATION_PATH = "M/44H/2H/0H/0";

    public static final String BCH_DERIVATION_PATH = "M/44H/145H/0H/0";

    public static final String ETH_DERIVATION_PATH = "M/44H/60H/0H/0";

    public static final String VET_DERIVATION_PATH = "M/44H/818H/0H/0";

    public static final String ADA_DERIVATION_PATH = "M/44H/1815H/0H/0/0";
}
