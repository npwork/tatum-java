package transaction

import org.kethereum.model.Address

/**
 * These are ERC20 Contract Addresses of Mainnet and probably will not work on Testnet.
 */
enum class Contract(val address: Address, val decimal: Int) {
    USDT(Address("0xdac17f958d2ee523a2206206994597c13d831ec7"), 6),
    LEO(Address("0x2af5d2ad76741191d15dfe7bf6ac92d4bd912ca3"), 18),
    LINK(Address("0x514910771af9ca656af840dff83e8264ecf986ca"), 18),
    FREE(Address("0x2f141ce366a2462f02cea3d12cf93e4dca49e4fd"), 18),
    MKR(Address("0x9f8f72aa9304c8b593d555f12ef6589cc3a579a2"), 18),
    USDC(Address("0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48"), 6),
    BAT(Address("0x0d8775f648430679a709e98d2b0cb6250d2887ef"), 18),
    TUSD(Address("0x0000000000085d4780B73119b644AE5ecd22b376"), 18),
    PAX(Address("0x8e870d67f660d95d5be530380d0ec0bd388289e1"), 18),
    PAXG(Address("0x45804880de22913dafe09f4980848ece6ecbaf78"), 18),
    PLTC(Address("0x429d83bb0dcb8cdd5311e34680adc8b12070a07f"), 18),
    MMY(Address("0x385ddf50c3de724f6b8ecb41745c29f9dd3c6d75"), 18),
    XCON(Address("0x0f237d5ea7876e0e2906034d98fdb20d43666ad4"), 18),
}
