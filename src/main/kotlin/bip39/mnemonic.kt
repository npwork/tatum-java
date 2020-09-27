package bip39

import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.validate

fun isValid(phrase: String): Boolean = MnemonicWords(phrase).validate(org.kethereum.bip39.wordlists.WORDLIST_ENGLISH)

/**
 * If no Mnemonic Phrase is present, new one is generated from english words.
 * Otherwise checks if MnemonicWords is a valid encoding according to the BIP39 spec.
 */
fun generateOrValidate(phrase: String? = null): String {
    return if (phrase == null) generateMnemonic(128, org.kethereum.bip39.wordlists.WORDLIST_ENGLISH)
    else if (isValid(phrase)) { phrase }
    else throw IllegalArgumentException()
}

fun generateRandomMnemonic() = generateOrValidate()
