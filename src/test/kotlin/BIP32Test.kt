import io.tatum.bip32.XPub
import io.tatum.bip32.toExtendedKey
import org.assertj.core.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.kethereum.bip32.generateChildKey
import org.kethereum.crypto.toAddress
import org.komputing.kbip44.BIP44Element

internal class BIP32Test {

    @ParameterizedTest
    @CsvSource(
        "0, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_0",
        "1, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1",
        "2, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_2",
        "3, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_3",
        "4, $TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_4",
    )
    fun `get address from xpub`(index: Int, expectedAddress: String) {
        val xpub = XPub(TESTNET_XPUB_OF_MNEM_15)
        val extendedKey = xpub.toExtendedKey()
            .generateChildKey(BIP44Element(false, index))

        val actualAddress = extendedKey.keyPair.toAddress().hex

        Assertions.assertThat(actualAddress).isEqualTo(expectedAddress)
    }
}