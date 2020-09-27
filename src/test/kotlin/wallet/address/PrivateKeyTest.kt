package wallet.address

import Currency
import MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_0
import MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_1
import MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_2
import MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_3
import MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_4
import TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_0
import TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_1
import TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_2
import TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_3
import TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_4
import VALID_MNEMONIC_15
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class PrivateKeyTest {

    @Nested
    inner class Generators {
        @Nested
        inner class Testnet {
            @ParameterizedTest
            @CsvSource(
                "0, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_0",
                "1, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_1",
                "2, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_2",
                "3, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_3",
                "4, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_4",
            )
            fun `get private key of mnemonic on testnet`(index: Int, privateKey: String) {
                val actualPrivateKey = generateEthPrivateKey(true, VALID_MNEMONIC_15, index)

                Assertions.assertThat(actualPrivateKey).isEqualTo(privateKey)
            }
        }

        @Nested
        inner class Mainnet {
            @ParameterizedTest
            @CsvSource(
                "0, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_0",
                "1, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_1",
                "2, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_2",
                "3, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_3",
                "4, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_4",
            )
            fun `get private key of mnemonic on mainnet`(index: Int, privateKey: String) {
                val actualPrivateKey = generateEthPrivateKey(false, VALID_MNEMONIC_15, index)

                Assertions.assertThat(actualPrivateKey).isEqualTo(privateKey)
            }
        }
    }

    @Nested
    inner class MainGenerator {
        @Nested
        inner class Testnet {
            @ParameterizedTest
            @CsvSource(
                "0, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_0",
                "1, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_1",
                "2, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_2",
                "3, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_3",
                "4, $TESTNET_PRIVATE_KEY_OF_MNEM_15_AT_4",
            )
            fun `get private key from mnemonic`(index: Int, privateKey: String) {
                val actualPrivateKey = generatePrivateKeyFromMnemonic(
                    Currency.ETH,
                    true,
                    VALID_MNEMONIC_15,
                    index
                )

                Assertions.assertThat(actualPrivateKey).isEqualTo(privateKey)
            }
        }

        @Nested
        inner class Mainnet {
            @ParameterizedTest
            @CsvSource(
                "0, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_0",
                "1, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_1",
                "2, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_2",
                "3, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_3",
                "4, $MAINNET_PRIVATE_KEY_OF_MNEM_15_AT_4",
            )
            fun `get private key from mnemonic`(index: Int, privateKey: String) {
                val actualPrivateKey = generatePrivateKeyFromMnemonic(
                    Currency.ETH,
                    false,
                    VALID_MNEMONIC_15,
                    index
                )

                Assertions.assertThat(actualPrivateKey).isEqualTo(privateKey)
            }
        }
    }

}