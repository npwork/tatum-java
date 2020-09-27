import bip39.generateOrValidate
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.toSeed
import org.kethereum.bip39.validate
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH

internal class BIP39Test {

    @Nested
    inner class Mnemonic {

        @ParameterizedTest
        @ValueSource(strings = [
            VALID_MNEMONIC_15,
            VALID_MNEMONIC_24,
        ])
        fun `valid mnemonic phrase`(phrase: String) {
            Assertions.assertThat(MnemonicWords(phrase).validate(WORDLIST_ENGLISH)).isTrue
        }

        @ParameterizedTest
        @ValueSource(strings = [
            INVALID_MNEMONIC_TOO_SHORT,
            INVALID_MNEMONIC_UNSUPPORTED_LANGUAGE,
            INVALID_MNEMONIC_ENDS_WITH_WHITESPACE,
            INVALID_MNEMONIC_STARTS_WITH_WHITESPACE,
            INVALID_MNEMONIC_CONTAINS_NON_ENGLISH_WORD,
        ])
        fun `mnemonic phrase in invalid phrase`(phrase: String) {
            val actualThrow: Throwable = Assertions.catchThrowable { generateOrValidate(phrase) }

            Assertions.assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `no mnemonic provided so generate new one`() {
            val actualNewMnemonicPhrase = generateOrValidate()
            println(actualNewMnemonicPhrase)

            Assertions.assertThat(MnemonicWords(actualNewMnemonicPhrase).validate(WORDLIST_ENGLISH)).isTrue
        }
    }

    @Nested
    inner class SeedTest {

        @Test
        fun `get seed as hex from mnemonic`() {
            val seed = MnemonicWords(VALID_MNEMONIC_15).toSeed("")

            Assertions.assertThat(seed.seed).isEqualTo(SEED_IN_BYTES_OF_MNEM_15)
        }
    }
}
