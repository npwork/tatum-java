package io.tatum.constants;

import io.tatum.model.request.Currency;
import io.tatum.network.EthMainNetParams;
import io.tatum.network.VetMainNetParams;
import io.tatum.network.LtcMainNetParams;
import io.tatum.network.LtcTestNet3Params;
import org.bitcoincashj.params.UnitTestParams;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.tatum.model.request.Currency.*;

/**
 * The type Constant.
 */
public class Constant {
    /**
     * The constant TATUM_API_URL.
     */
    public static final String TATUM_API_URL = "https://api-eu1.tatum.io";

    /**
     * The constant TEST_VET_URL.
     */
    public static final String TEST_VET_URL = "https://sync-testnet.vechain.org/";
    /**
     * The constant VET_URL.
     */
    public static final String VET_URL = "https://sync-mainnet.vechain.org/";

    /**
     * The constant EMPTY_BODY.
     */
    public static final String EMPTY_BODY = "{}";

    /**
     * The constant BITCOIN_MAINNET.
     */
    public static final NetworkParameters BITCOIN_MAINNET = MainNetParams.get();

    /**
     * The constant BITCOIN_TESTNET.
     */
    public static final NetworkParameters BITCOIN_TESTNET = TestNet3Params.get();

    /**
     * The constant BCH_MAINNET.
     */
    public static final org.bitcoincashj.core.NetworkParameters BCH_MAINNET = org.bitcoincashj.params.MainNetParams.get();

    /**
     * The constant BCH_TESTNET.
     */
    public static final org.bitcoincashj.core.NetworkParameters BCH_TESTNET = org.bitcoincashj.params.TestNet3Params.get();

    /**
     * The constant LITECOIN_MAINNET.
     */
    public static final NetworkParameters LITECOIN_MAINNET = LtcMainNetParams.get();

    /**
     * The constant LITECOIN_TESTNET.
     */
    public static final NetworkParameters LITECOIN_TESTNET = LtcTestNet3Params.get();

    /**
     * The constant ETHEREUM_MAINNET.
     */
    public static final NetworkParameters ETHEREUM_MAINNET = EthMainNetParams.get();

    /**
     * The constant VET_MAINNET.
     */
    public static final NetworkParameters VET_MAINNET = VetMainNetParams.get();


    /**
     * The constant TESTNET_DERIVATION_PATH.
     */
// TESTNET_DERIVATION_PATH = 'm/44\'/1\'/0\'/0'
    public static final String TESTNET_DERIVATION_PATH = "M/44H/1H/0H/0";

    /**
     * The constant BTC_DERIVATION_PATH.
     */
// BTC_DERIVATION_PATH = 'm/44\'/0\'/0\'/0';
    public static final String BTC_DERIVATION_PATH = "M/44H/0H/0H/0";

    /**
     * The constant LTC_DERIVATION_PATH.
     */
//LTC_DERIVATION_PATH = 'm/44\'/2\'/0\'/0';
    public static final String LTC_DERIVATION_PATH = "M/44H/2H/0H/0";

    /**
     * The constant BCH_DERIVATION_PATH.
     */
    public static final String BCH_DERIVATION_PATH = "M/44H/145H/0H/0";

    /**
     * The constant ETH_DERIVATION_PATH.
     */
    public static final String ETH_DERIVATION_PATH = "M/44H/60H/0H/0";

    /**
     * The constant VET_DERIVATION_PATH.
     */
    public static final String VET_DERIVATION_PATH = "M/44H/818H/0H/0";

    /**
     * The constant ADA_DERIVATION_PATH.
     */
    public static final String ADA_DERIVATION_PATH = "M/44H/1815H/0H/0/0";

    /**
     * The constant TRANSFER_METHOD_ABI.
     */
    public static final String TRANSFER_METHOD_ABI = "{" +
            "   \"constant\":false," +
            "   \"inputs\":[" +
            "      {" +
            "         \"name\":\"to\"," +
            "         \"type\":\"address\"" +
            "      }," +
            "      {" +
            "         \"name\":\"value\"," +
            "         \"type\":\"uint256\"" +
            "      }" +
            "   ]," +
            "   \"name\":\"transfer\"," +
            "   \"outputs\":[" +
            "      {" +
            "         \"name\":\"\"," +
            "         \"type\":\"bool\"" +
            "      }" +
            "   ]," +
            "   \"payable\":false," +
            "   \"stateMutability\":\"nonpayable\"," +
            "   \"type\":\"function\"" +
            "}";

    /**
     * The constant CONTRACT_ADDRESSES.
     */
    public static final Map<String, String> CONTRACT_ADDRESSES = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(USDT.toString(), "0xdac17f958d2ee523a2206206994597c13d831ec7"),
            new AbstractMap.SimpleEntry<>(LEO.toString(), "0x2af5d2ad76741191d15dfe7bf6ac92d4bd912ca3"),
            new AbstractMap.SimpleEntry<>(UNI.toString(), "0x1f9840a85d5af5bf1d1762f925bdaddc4201f984"),
            new AbstractMap.SimpleEntry<>(LINK.toString(), "0x514910771af9ca656af840dff83e8264ecf986ca"),
            new AbstractMap.SimpleEntry<>(FREE.toString(), "0x2f141ce366a2462f02cea3d12cf93e4dca49e4fd"),
            new AbstractMap.SimpleEntry<>(MKR.toString(), "0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2"),
            new AbstractMap.SimpleEntry<>(USDC.toString(), "0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48"),
            new AbstractMap.SimpleEntry<>(BAT.toString(), "0x0d8775f648430679a709e98d2b0cb6250d2887ef"),
            new AbstractMap.SimpleEntry<>(TUSD.toString(), "0x0000000000085d4780B73119b644AE5ecd22b376"),
            new AbstractMap.SimpleEntry<>(PAX.toString(), "0x8e870d67f660d95d5be530380d0ec0bd388289e1"),
            new AbstractMap.SimpleEntry<>(PAXG.toString(), "0x45804880de22913dafe09f4980848ece6ecbaf78"),
            new AbstractMap.SimpleEntry<>(PLTC.toString(), "0x429d83bb0dcb8cdd5311e34680adc8b12070a07f"),
            new AbstractMap.SimpleEntry<>(MMY.toString(), "0x385ddf50c3de724f6b8ecb41745c29f9dd3c6d75"),
            new AbstractMap.SimpleEntry<>(XCON.toString(), "0x0f237d5ea7876e0e2906034d98fdb20d43666ad4")
    );


    /**
     * The constant CONTRACT_DECIMALS.
     */
    public static final Map<String, Integer> CONTRACT_DECIMALS = Map.ofEntries(
            new AbstractMap.SimpleEntry<>(USDT.toString(), 6),
            new AbstractMap.SimpleEntry<>(LEO.toString(), 18),
            new AbstractMap.SimpleEntry<>(UNI.toString(), 18),
            new AbstractMap.SimpleEntry<>(LINK.toString(), 18),
            new AbstractMap.SimpleEntry<>(FREE.toString(), 18),
            new AbstractMap.SimpleEntry<>(MKR.toString(), 18),
            new AbstractMap.SimpleEntry<>(USDC.toString(), 6),
            new AbstractMap.SimpleEntry<>(BAT.toString(), 18),
            new AbstractMap.SimpleEntry<>(TUSD.toString(), 18),
            new AbstractMap.SimpleEntry<>(PAX.toString(), 18),
            new AbstractMap.SimpleEntry<>(PAXG.toString(), 18),
            new AbstractMap.SimpleEntry<>(PLTC.toString(), 18),
            new AbstractMap.SimpleEntry<>(MMY.toString(), 18),
            new AbstractMap.SimpleEntry<>(XCON.toString(), 18)
    );


    /**
     * The constant ETH_BASED_CURRENCIES.
     */
    public static final List<Currency> ETH_BASED_CURRENCIES = new ArrayList<>(Arrays.asList(USDT,
            LEO,
            UNI,
            LINK,
            FREE,
            MKR,
            USDC,
            BAT,
            TUSD,
            PAX,
            PAXG,
            PLTC,
            MMY,
            XCON,
            ETH));

}
