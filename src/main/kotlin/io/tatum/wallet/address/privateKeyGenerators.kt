package io.tatum.wallet.address

import io.tatum.Derivation
import org.kethereum.bip32.generateChildKey
import org.kethereum.bip32.toKey
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed
import org.kethereum.extensions.toHexStringZeroPadded

/**
 * Generate Bitcoin private key from mnemonic seed
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generateBtcPrivateKey(testnet: Boolean, mnemonic: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate Litecoin private key from mnemonic seed
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generateLtcPrivateKey(testnet: Boolean, mnemonic: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate Bitcoin Cash private key from mnemonic seed
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generateBchPrivateKey(testnet: Boolean, mnemonic: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate Ethereum or any other ERC20 private key from mnemonic seed
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generateEthPrivateKey(testnet: Boolean, mnemonic: String, i: Int): String {
    val path = if (testnet) Derivation.TESTNET.path else Derivation.ETH.path
    val seed = MnemonicWords(mnemonic).toSeed()
    val prvKey = seed.toKey(path)
        .generateChildKey(org.komputing.kbip44.BIP44Element(false, i))

    return prvKey.keyPair.privateKey.key.toHexStringZeroPadded(64, true)
}

/**
 * Generate VeChain private key from mnemonic seed
 * @param testnet testnet or mainnet version of wallet.address
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generateVetPrivateKey(testnet: Boolean, mnemonic: String, i: Int): String = TODO("Will be implemented later.")

/**
 * Generate BnbB private key from mnemonic seed
 * @param mnemonic mnemonic to generate private key from
 * @param i derivation index of private key to generate.
 * @returns blockchain private key to the wallet.address
 */
fun generateBnbPrivateKey(mnemonic: String, i: Int): String = TODO("Will be implemented later.")
