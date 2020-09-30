package io.tatum.wallet.address

import io.tatum.bip32.XPub
import io.tatum.bip32.toExtendedKey
import org.kethereum.bip32.generateChildKey
import org.kethereum.crypto.toAddress
import org.komputing.kbip44.BIP44Element

/**
 * Generate Bitcoin wallet.address
 * @param testnet testnet or mainnet version of wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateBtcAddress(testnet: Boolean, xpub: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate Litecoin wallet.address
 * @param testnet testnet or mainnet version of wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateLtcAddress(testnet: Boolean, xpub: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate Bitcoin Cash wallet.address
 * @param testnet testnet or mainnet version of wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateBchAddress(testnet: Boolean, xpub: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate Ethereum or any other ERC20 wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateEthAddress(xpub: String, i: Int): String {
    val xPublicKey = XPub(xpub)
    val extendedKey = xPublicKey.toExtendedKey()
        .generateChildKey(BIP44Element(false, i))

    return extendedKey.keyPair.toAddress().hex
}

/**
 * Generate VeChain wallet.address
 * @param testnet testnet or mainnet version of wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateVetAddress(testnet: Boolean, xpub: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate BnB wallet.address
 * @param testnet testnet or mainnet version of wallet.address
 * @param xpub extended public key to generate wallet.address from
 * @param i derivation index of wallet.address to generate. Up to 2^32 addresses can be generated.
 * @returns blockchain wallet.address
 */
fun generateBnbAddress(testnet: Boolean, xpub: String, i: Int): String = TODO("Will be implemented later.")
