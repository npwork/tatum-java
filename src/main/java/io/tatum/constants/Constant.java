package io.tatum.constants;

import io.tatum.utils.LtcMainNetParams;
import io.tatum.utils.LtcTestNet3Params;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.ChildNumber;
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
    public static final ChildNumber[] TESTNET_DERIVATION_PATH = new ChildNumber[]{new ChildNumber(44, true), new ChildNumber(1, true), new ChildNumber(0, false), new ChildNumber(0, false)};

    // BTC_DERIVATION_PATH = 'm/44\'/0\'/0\'/0';
    public static final ChildNumber[] BTC_DERIVATION_PATH = new ChildNumber[]{new ChildNumber(44, true), new ChildNumber(0, false), new ChildNumber(0, false), new ChildNumber(0, false)};

    //LTC_DERIVATION_PATH = 'm/44\'/2\'/0\'/0';
    public static final ChildNumber[] LTC_DERIVATION_PATH = new ChildNumber[]{new ChildNumber(44, true), new ChildNumber(2, true), new ChildNumber(0, false), new ChildNumber(0, false)};

}
