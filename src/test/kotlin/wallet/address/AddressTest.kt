package wallet.address

import MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_0
import MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_1
import MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_2
import MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_3
import MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_4
import MAINNET_XPUB_OF_MNEM_15
import TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_0
import TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1
import TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_2
import TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_3
import TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_4
import TESTNET_XPUB_OF_MNEM_15
import io.tatum.Currency
import io.tatum.wallet.address.generateAddressFromXPub
import io.tatum.wallet.address.generateEthAddress
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class AddressTest {

    @Nested
    inner class Generators {
        @Nested
        inner class Testnet {
            @ParameterizedTest
            @CsvSource(
                "0, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_0",
                "1, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1",
                "2, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_2",
                "3, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_3",
                "4, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_4",
            )
            fun `get eth address from xpub`(index: Int, expectedAddress: String) {
                val actualAddress = generateEthAddress(TESTNET_XPUB_OF_MNEM_15, index)

                Assertions.assertThat(actualAddress).isEqualTo(expectedAddress)
            }
        }

        @Nested
        inner class Mainnet {
            @ParameterizedTest
            @CsvSource(
                "0, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_0",
                "1, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_1",
                "2, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_2",
                "3, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_3",
                "4, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_4",
            )
            fun `get eth address from xpub`(index: Int, expectedAddress: String) {
                val actualAddress = generateEthAddress(MAINNET_XPUB_OF_MNEM_15, index)

                Assertions.assertThat(actualAddress).isEqualTo(expectedAddress)
            }
        }
    }

    @Nested
    inner class MainGenerator {
        @Nested
        inner class Testnet {
            @ParameterizedTest
            @CsvSource(
                "0, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_0",
                "1, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1",
                "2, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_2",
                "3, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_3",
                "4, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_4",
            )
            fun `get eth address from xpub`(index: Int, expectedAddress: String) {
                val actualAddress =
                    generateAddressFromXPub(Currency.ETH, true, TESTNET_XPUB_OF_MNEM_15, index)

                Assertions.assertThat(actualAddress).isEqualTo(expectedAddress)
            }
        }

        @Nested
        inner class Mainnet {
            @ParameterizedTest
            @CsvSource(
                "0, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_0",
                "1, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_1",
                "2, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_2",
                "3, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_3",
                "4, $MAINNET_ETH_ADDRESS_OF_MNEM_15_AT_4",
            )
            fun `get eth address from xpub`(index: Int, expectedAddress: String) {
                val actualAddress =
                    generateAddressFromXPub(Currency.ETH, false, MAINNET_XPUB_OF_MNEM_15, index)

                Assertions.assertThat(actualAddress).isEqualTo(expectedAddress)
            }
        }
    }
}