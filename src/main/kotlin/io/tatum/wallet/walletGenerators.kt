package io.tatum.wallet

import io.tatum.Derivation
import org.kethereum.bip32.model.Seed
import org.kethereum.bip32.toKey
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed

/**
 * Generate Bitcoin wallet
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic seed to use
 * @returns wallet
 */
internal fun generateBtcWallet(testnet: Boolean, mnemonic: String): Wallet = TODO("Will be implemented later")

/**
 * Generate Litecoin wallet
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic seed to use
 * @returns wallet
 */
internal fun generateLtcWallet(testnet: Boolean, mnemonic: String): Wallet = TODO("Will be implemented later")

/**
 * Generate Bitcoin Cash wallet
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic seed to use
 * @returns wallet
 */
internal fun generateBchWallet(testnet: Boolean, mnemonic: String): Wallet = TODO("Will be implemented later")

/**
 * Generate Ethereum or any other ERC20 wallet
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic seed to use
 * @returns wallet
 */
fun generateEthWallet(testnet: Boolean, mnemonic: String): Wallet {
    val path = if (testnet) Derivation.TESTNET.path else Derivation.ETH.path
    val seed: Seed = MnemonicWords(mnemonic).toSeed()
    val derivedKey = seed.toKey(path, false)
    return Wallet(
        xpub = derivedKey.serialize(true),
        mnemonic = mnemonic
    )
}

/**
 * Generate Xrp wallet.address and secret.
 */
internal fun generateXrpWallet(): Wallet = TODO("Will be implemented later")

/**
 * Generate Stellar wallet.address and secret.
 * @param secret secret of the account to generate wallet.address
 */
internal fun generateXlmWallet(): Wallet = TODO("Will be implemented later")

/**
 * Generate VeChain wallet
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic seed to use
 * @returns wallet
 */
internal fun generateVetWallet(testnet: Boolean, mnemonic: String): Wallet = TODO("Will be implemented later")

/**
 * Generate Neo wallet.address and private key.
 */
internal fun generateNeoWallet(): Wallet = TODO("Will be implemented later")

/**
 * Generate BnB wallet
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic seed to use
 * @returns wallet
 */
internal fun generateBnbWallet(testnet: Boolean, mnemonic: String): Wallet = TODO("Will be implemented later")
