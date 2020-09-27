package transaction

import Currency
import FAUCET_MNEMONIC
import blockchain.ethGetTransactionsCount
import model.request.TransferEthErc20
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.kethereum.crypto.toAddress
import org.kethereum.crypto.toECKeyPair
import org.kethereum.model.Address
import org.kethereum.model.PrivateKey
import org.komputing.khex.model.HexString
import wallet.address.generateEthPrivateKey
import java.math.BigDecimal

private val faucetPrivateKeyOf0 =
    generateEthPrivateKey(true, FAUCET_MNEMONIC, 0)

private val faucetAddressOf0 = PrivateKey(HexString(faucetPrivateKeyOf0)).toECKeyPair().toAddress()

private val toAddress1 = Address("0x8cb76aed9c5e336ef961265c6079c14e9cd3d2ea")
private val toAddress2 = Address("0xbd53bf6573321d159453f09e71b053d5dbf5ce31")

internal class EthKtTest {

    @Nested
    inner class PrepareEthErc20Test {

        @Test
        fun `prepare eth transaction`() {
            val body = TransferEthErc20(
                fromPrivateKey = faucetPrivateKeyOf0,
                to = toAddress1,
                nonce = ethGetTransactionsCount(faucetAddressOf0),
                amount = BigDecimal.ZERO,
                currency = Currency.ETH,
            )

            val actualSignedTransaction = prepareEthOrErc20SignedTransaction(true, body)

            Assertions.assertThat(actualSignedTransaction).startsWith("0x")
        }

        @Test
        fun `prepare pltc transaction`() {
            val body = TransferEthErc20(
                fromPrivateKey = faucetPrivateKeyOf0,
                to = toAddress1,
                nonce = ethGetTransactionsCount(faucetAddressOf0),
                amount = BigDecimal.ZERO,
                currency = Currency.PLTC,
            )

            val actualSignedTransaction = prepareEthOrErc20SignedTransaction(true, body)

            Assertions.assertThat(actualSignedTransaction).startsWith("0x")
        }
    }

    @Nested
    inner class SendEthErc20Test {

        @Test
        fun `send eth transaction`() {
            val body = TransferEthErc20(
                fromPrivateKey = faucetPrivateKeyOf0,
                to = toAddress2,
                nonce = ethGetTransactionsCount(faucetAddressOf0),
                amount = BigDecimal.valueOf(0.000001),
                currency = Currency.ETH,
            )

            val actualSignedTransaction = sendEthOrErc20Transaction(true, body)

            Assertions.assertThat(actualSignedTransaction)
        }

        // TODO(Martin) add `send pltc transaction`
    }
}
