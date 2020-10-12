import io.tatum.Currency
import io.tatum.bip39.generateRandomMnemonic
import io.tatum.blockchain.ethGetTransaction
import io.tatum.blockchain.ethGetTransactionsCount
import io.tatum.ledger.createAccount
import io.tatum.ledger.getAccountBalance
import io.tatum.model.request.CreateAccount
import io.tatum.model.request.TransferEthErc20
import io.tatum.offchain.generateDepositAddress
import io.tatum.transaction.sendEthOrErc20Transaction
import io.tatum.wallet.address.generateEthPrivateKey
import io.tatum.wallet.address.generatePrivateKeyFromMnemonic
import io.tatum.wallet.generateEthWallet
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.kethereum.crypto.toAddress
import org.kethereum.crypto.toECKeyPair
import org.kethereum.model.Address
import org.kethereum.model.PrivateKey
import org.komputing.khex.model.HexString
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.random.nextInt

private val faucetPrivateKeyOf0 =
    generateEthPrivateKey(true, FAUCET_MNEMONIC, 0)

private val faucetAddressOf0 = PrivateKey(HexString(faucetPrivateKeyOf0)).toECKeyPair().toAddress()

@Disabled
internal class Demo {

    @Test
    fun intro() {

        // Generate random Mnemonic
        val randomMnemonic = generateRandomMnemonic()

        // Use mnemonic to generate Ethereum Wallet
        val wallet = generateEthWallet(true, randomMnemonic).also {
            println("mnemonic: ${it.mnemonic}")
            println("xpub: ${it.xpub}")
        }

        val prvKey = generatePrivateKeyFromMnemonic(
            currency = Currency.ETH,
            testnet = true,
            mnemonic = randomMnemonic,
            i = 0,
        ).also { println("prvKey: $it") }

        // Create account at Tatum
        val prepareAccount = CreateAccount(
            currency = "ETH",
            xpub = wallet.xpub,
            accountCode = "demo-account-${Random.nextInt(1..100000)}",
        ).also { println("accountCode: ${it.accountCode}") }

        val tatumAccount = createAccount(prepareAccount).also {
            println("tatumAccount.id: ${it.id}")
        }

        // Generate deposit address
        val tatumAccountAddressResponse = generateDepositAddress(tatumAccount.id)
        val tatumAccountAddress = Address(tatumAccountAddressResponse.address).also {
            println("Deposite address: ${it.hex}")
        }

        // Create eth transfer body
        val prepareTransferBody = TransferEthErc20(
            fromPrivateKey = faucetPrivateKeyOf0,
            to = tatumAccountAddress,
            nonce = ethGetTransactionsCount(faucetAddressOf0).also { println("Last nonce: $it") },
            currency = Currency.ETH,
            amount = BigDecimal.valueOf(0.000001),
        )

        // Send 0.000001 ether to tatum account address
        val transaction = sendEthOrErc20Transaction(
            testnet = true,
            body = prepareTransferBody,
        ).also {
            println("You can check transaction at https://ropsten.etherscan.io/tx/${it.txId}")
        }

        // Get transaction status
        Thread.sleep(60L*1000L) // wait till transaction is processed at Tatum backend
        ethGetTransaction(transaction).also {
            println("From faucet tx status: ${it.status}")
        }

        // After a few confirmations, balance should be updated to 0.000001 ether
        val accountBalance = getAccountBalance(tatumAccount.id).also {
            println("Account balance: ${it.accountBalance}")
        }

    }
}
