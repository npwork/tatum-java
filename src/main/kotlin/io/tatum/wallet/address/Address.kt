package io.tatum.wallet.address

import io.tatum.Currency

const val UNSUPPORTED_BLOCKCHAIN = "Unsupported blockchain."

/**
 * Generate wallet.address
 * @param currency type of blockchain
 * @param testnet testnet or mainnet version of wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateAddressFromXPub(currency: Currency, testnet: Boolean, xpub: String, i: Int): String {
    return when (currency) {
        Currency.BTC -> generateBtcAddress(testnet, xpub, i)
        Currency.LTC -> generateLtcAddress(testnet, xpub, i)
        Currency.BCH -> generateBchAddress(testnet, xpub, i)
        Currency.USDT, Currency.LEO, Currency.LINK, Currency.FREE, Currency.MKR, Currency.USDC, Currency.BAT,
        Currency.TUSD, Currency.PAX, Currency.PAXG, Currency.PLTC, Currency.XCON, Currency.ETH, Currency.MMY
        -> generateEthAddress(xpub, i)
        Currency.VET -> generateVetAddress(testnet, xpub, i)
        Currency.BNB -> generateBnbAddress(testnet, xpub, i)
        else -> throw Error(UNSUPPORTED_BLOCKCHAIN)
    }
}

/**
 * Generate private key from mnemonic seed
 * @param currency type of blockchain
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generatePrivateKeyFromMnemonic(currency: Currency, testnet: Boolean, mnemonic: String, i: Int): String {
    return when (currency) {
        Currency.BTC -> generateBtcPrivateKey(testnet, mnemonic, i)
        Currency.LTC -> generateLtcPrivateKey(testnet, mnemonic, i)
        Currency.BCH -> generateBchPrivateKey(testnet, mnemonic, i)
        Currency.USDT, Currency.LEO, Currency.LINK, Currency.FREE, Currency.MKR, Currency.USDC, Currency.BAT,
        Currency.TUSD, Currency.PAX, Currency.PAXG, Currency.PLTC, Currency.XCON, Currency.ETH, Currency.MMY
        -> generateEthPrivateKey(testnet, mnemonic, i)
        Currency.VET -> generateVetPrivateKey(testnet, mnemonic, i)
        Currency.BNB -> generateBnbPrivateKey(mnemonic, i)
        else -> throw Error(UNSUPPORTED_BLOCKCHAIN)
    }
}
