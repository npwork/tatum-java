package wallet

import Currency
import MAINNET_ETH_WALLET_OF_MNEM_15
import TESTNET_ETH_WALLET_OF_MNEM_15
import TESTNET_XPRV_OF_MNEM_15
import TESTNET_XPUB_OF_MNEM_15
import VALID_MNEMONIC_15
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.kethereum.bip39.model.MnemonicWords
import org.kethereum.bip39.validate
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH

internal class WalletTest {

    @Nested
    inner class CoinGenerators {

        @Test
        fun `generate ETH wallet from mnemonic on testnet`() {
            val actualWallet: Wallet = generateEthWallet(testnet = true, mnemonic = VALID_MNEMONIC_15)

            assertThat(actualWallet).hasSameHashCodeAs(TESTNET_ETH_WALLET_OF_MNEM_15)
        }

        @Test
        fun `generate ETH wallet from mnemonic on mainnet`() {
            val actualWallet: Wallet = generateEthWallet(testnet = true, mnemonic = VALID_MNEMONIC_15)

            assertThat(actualWallet).hasSameHashCodeAs(TESTNET_ETH_WALLET_OF_MNEM_15)
        }
    }

    @Nested
    inner class MainGenerator {

        @Test
        fun `generate ETH wallet without given mnemonic on testnet`() {
            val actualWallet: Wallet = generateWallet(Currency.ETH, true)

            assertThat(actualWallet)
                .hasNoNullFieldsOrProperties()
            assertThat(actualWallet.xpub).startsWith("xpub").hasSameSizeAs(TESTNET_XPUB_OF_MNEM_15)
            assertThat(actualWallet.xprv).startsWith("xprv").hasSameSizeAs(TESTNET_XPRV_OF_MNEM_15)
            assertThat(MnemonicWords(actualWallet.mnemonic).validate(WORDLIST_ENGLISH)).isTrue
        }

        @Test
        fun `generate ETH wallet with given mnemonic on testnet`() {
            val actualWallet: Wallet = generateWallet(Currency.ETH, true, VALID_MNEMONIC_15)

            assertThat(actualWallet).hasSameHashCodeAs(TESTNET_ETH_WALLET_OF_MNEM_15)
        }

        @Test
        fun `generate ETH wallet with given mnemonic on mainnet`() {
            val actualWallet: Wallet = generateWallet(Currency.ETH, false, VALID_MNEMONIC_15)

            assertThat(actualWallet).hasSameHashCodeAs(MAINNET_ETH_WALLET_OF_MNEM_15)
        }
    }
}
