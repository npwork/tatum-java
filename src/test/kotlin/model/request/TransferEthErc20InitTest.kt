package model.request

import FAUCET_MNEMONIC
import io.tatum.Currency
import io.tatum.blockchain.ethGetTransactionsCount
import io.tatum.model.request.TransferEthErc20
import io.tatum.wallet.address.generateEthPrivateKey
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.kethereum.model.Address
import java.math.BigDecimal
import java.math.BigInteger

private val privateKeyOf0 =
    generateEthPrivateKey(true, FAUCET_MNEMONIC, 0)

private val toAddress = Address("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea")

internal class TransferEthErc20InitTest {

    @Test
    fun `create instance with valid parameters`() {
        val actualTransfer = TransferEthErc20(
            fromPrivateKey = privateKeyOf0,
            to = toAddress,
            nonce = ethGetTransactionsCount(toAddress),
            amount = BigDecimal.ZERO,
            currency = Currency.ETH,
        )

        assertThat(actualTransfer)
            .isInstanceOf(TransferEthErc20::class.java)
            .hasNoNullFieldsOrPropertiesExcept("nonce")
        assertThat(actualTransfer.fee)
            .hasNoNullFieldsOrProperties()
    }

    @Test
    fun `fail creating instance with negative nonce`() {
        val actualThrow: Throwable = Assertions.catchThrowable {
            TransferEthErc20(
                fromPrivateKey = privateKeyOf0,
                to = toAddress,
                amount = BigDecimal.ZERO,
                currency = Currency.ETH,
                nonce = BigInteger.TEN.negate(),
            )
        }

        assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `fail creating instance with input field exceeding max range 130000`() {
        val actualThrow: Throwable = Assertions.catchThrowable {
            TransferEthErc20(
                fromPrivateKey = privateKeyOf0,
                to = toAddress,
                nonce = ethGetTransactionsCount(toAddress),
                amount = BigDecimal.ZERO,
                currency = Currency.ETH,
                data = "A".repeat(130001)
            )
        }

        assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "0x0cfa57cd6d2c2a950727f01f4131049b8cc43db20cd7fd4609ca183998f64d6eTooBig",
        "0x0cfa57cd6d2c2a950727f01f413104tooSmall",
    ])
    fun `fail creating instance with invalid fromPrivateKey length`(fromPrivateKey: String) {
        val actualThrow: Throwable = Assertions.catchThrowable {
            TransferEthErc20(
                fromPrivateKey = fromPrivateKey,
                to = toAddress,
                nonce = ethGetTransactionsCount(toAddress),
                amount = BigDecimal.ZERO,
                currency = Currency.ETH,
            )
        }

        assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
    }

    @ParameterizedTest
    @ValueSource(strings = [
        "0x8cb76aed9c5e336ef961265c6079c14e9cd3d2eaTooBig",
        "0x8cb76aed9c5e336eTooSmall",
    ])
    fun `fail creating instance with invalid to (=address) length`(to: String) {
        val actualThrow: Throwable = Assertions.catchThrowable {
            TransferEthErc20(
                fromPrivateKey = privateKeyOf0,
                to = Address(to),
                nonce = ethGetTransactionsCount(Address(to)),
                amount = BigDecimal.ZERO,
                currency = Currency.ETH,
            )
        }

        assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
    }
}
