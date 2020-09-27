package wallet

import Currency
import bip39.generateOrValidate

data class Wallet(
    val xpub: String,
    val xprv: String,
    val mnemonic: String,
)

/**
 * Generate wallet
 * @param currency blockchain to generate wallet for
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic english mnemonic seed to use. If not present, new one will be generated
 * @returns wallet or a combination of wallet.address and private key
 */
fun generateWallet(currency: Currency, testnet: Boolean, mnemonic: String? = null): Wallet {
    val mnem: String = generateOrValidate(mnemonic)

    return when (currency) {
        Currency.BTC -> generateBtcWallet(testnet, mnem)
        Currency.LTC -> generateLtcWallet(testnet, mnem)
        Currency.BCH -> generateBchWallet(testnet, mnem)
        Currency.USDT, Currency.LEO, Currency.LINK, Currency.FREE, Currency.MKR, Currency.USDC, Currency.BAT,
        Currency.TUSD, Currency.PAX, Currency.PAXG, Currency.PLTC, Currency.XCON, Currency.ETH, Currency.MMY
        -> generateEthWallet(testnet, mnem)
        Currency.XRP -> generateXrpWallet()
        Currency.XLM -> generateXlmWallet()
        Currency.VET -> generateVetWallet(testnet, mnem)
        Currency.NEO -> generateNeoWallet()
        Currency.BNB -> generateBnbWallet(testnet, mnem)
    }
}