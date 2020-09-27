package offchain

import Currency
import FAUCET_MNEMONIC
import TESTNET_XPUB_OF_MNEM_15
import blockchain.ethGetTransactionsCount
import ledger.CUSTOMER
import ledger.PREPARED_ACCOUNT_WITHOUT_BLOCKCHAIN_ADDRESS
import ledger.PREPARED_ACCOUNT_WITH_BLOCKCHAIN_ADDRESS
import ledger.createAccount
import model.request.TransferEthErc20
import model.request.Withdrawal
import model.response.common.TatumException
import model.response.ledger.Account
import model.response.offchain.Address
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.kethereum.crypto.toAddress
import org.kethereum.crypto.toECKeyPair
import org.kethereum.model.PrivateKey
import org.komputing.khex.model.HexString
import transaction.sendEthOrErc20Transaction
import wallet.address.generateEthAddress
import wallet.address.generateEthPrivateKey
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.random.nextInt

private lateinit var TEST_ACCOUNT_WITH_BLOCKCHAIN: Account
private lateinit var TEST_ACCOUNT_WITHOUT_BLOCKCHAIN: Account
private lateinit var DEPOSIT_ADDRESS_WITH_BLOCKCHAIN: Address

private val faucetPrivateKeyOf0 =
    generateEthPrivateKey(true, FAUCET_MNEMONIC, 0)

private val faucetAddressOf0 = PrivateKey(HexString(faucetPrivateKeyOf0)).toECKeyPair().toAddress()

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommonKtTest {

    @BeforeAll
    fun createTestAccountsWithAndWithoutDepositAddress() {
        // Create Account + Deposit Address assigned to blockchain address
        TEST_ACCOUNT_WITH_BLOCKCHAIN = createAccount(PREPARED_ACCOUNT_WITH_BLOCKCHAIN_ADDRESS)
        println("New testing account with assigned blockchain address has an id ${TEST_ACCOUNT_WITH_BLOCKCHAIN.id}")
        DEPOSIT_ADDRESS_WITH_BLOCKCHAIN = generateDepositAddress(TEST_ACCOUNT_WITH_BLOCKCHAIN.id)
        println("New deposit address with assigned blockchain address was created ${DEPOSIT_ADDRESS_WITH_BLOCKCHAIN.address}")

        // Create Account with NO relation to blockchain address
        TEST_ACCOUNT_WITHOUT_BLOCKCHAIN = createAccount(PREPARED_ACCOUNT_WITHOUT_BLOCKCHAIN_ADDRESS)
        println("New testing account without assigned blockchain address has an id ${TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id}")
    }

    @Test
    fun `generate deposit address for account with assigned blockchain`() {
        assertThat(DEPOSIT_ADDRESS_WITH_BLOCKCHAIN).satisfies { it.xpub!!.startsWith("xpub") }
    }

    @Test
    fun `fail generate deposit address for account without assigned blockchain`() {
        val actualThrow: Throwable = Assertions.catchThrowable {
            generateDepositAddress(TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id)
        }
        assertThat(actualThrow).isInstanceOf(TatumException::class.java)
    }

    @Test
    fun checkAddressExistsTest() {
        val actualAccount =
            checkAddressExists(
                org.kethereum.model.Address(DEPOSIT_ADDRESS_WITH_BLOCKCHAIN.address),
                Currency.valueOf(DEPOSIT_ADDRESS_WITH_BLOCKCHAIN.currency),
            )

        assertThat(actualAccount).satisfies {
            it.id == TEST_ACCOUNT_WITH_BLOCKCHAIN.id
            it.currency == DEPOSIT_ADDRESS_WITH_BLOCKCHAIN.currency
            it.accountingCurrency == CUSTOMER.accountingCurrency
        }
    }

    // this method tests getDepositAddressesForAccount, assignDepositAddress, removeDepositAddress
    @Test
    fun unAssignDepositAddressTest() {
        assertThat(getDepositAddressesForAccount(TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id)).isEmpty()

        val actualAssignedDeposit = assignDepositAddress(
            TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id,
            org.kethereum.model.Address(
                generateEthAddress(TESTNET_XPUB_OF_MNEM_15, Random.nextInt(1000..1000000000).also {
                    println("Random index: $it")
                }).also {
                    println("Generated address: $it")
                }
            ),
        )

        assertThat(getDepositAddressesForAccount(TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id)).hasOnlyOneElementSatisfying {
            it.hashCode() == actualAssignedDeposit.hashCode()
        }

        removeDepositAddress(
            TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id,
            org.kethereum.model.Address(actualAssignedDeposit.address)
        )

        assertThat(getDepositAddressesForAccount(TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id)).isEmpty()
    }
}

lateinit var withdrawal: Withdrawal

// TODO(Martin) wait/delay is required for transactional tests. That should be done in integration tests, not unit.
@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class WithdrawalTest {

    @BeforeAll
    fun createTestAccountsWithDepositAddress() {
        // Create Account + Deposit Address assigned to blockchain address
        TEST_ACCOUNT_WITH_BLOCKCHAIN = createAccount(PREPARED_ACCOUNT_WITH_BLOCKCHAIN_ADDRESS)
        println("New testing account with assigned blockchain address has an id ${TEST_ACCOUNT_WITH_BLOCKCHAIN.id}")
        DEPOSIT_ADDRESS_WITH_BLOCKCHAIN = generateDepositAddress(TEST_ACCOUNT_WITH_BLOCKCHAIN.id)
        println("New deposit address with assigned blockchain address was created ${DEPOSIT_ADDRESS_WITH_BLOCKCHAIN.address}")

        withdrawal = Withdrawal(
            senderAccountId = TEST_ACCOUNT_WITH_BLOCKCHAIN.id,
            address = faucetAddressOf0.hex,
            amount = BigDecimal.ZERO,
        )
    }

    @Test
    fun offchainBroadcastTest() {
    }

    // Send withdrawal back to faucet address.
    @Test
    fun offchainStoreWithdrawalTest() {
        val actualStoredWithdrawal = offchainStoreWithdrawal(withdrawal)

        assertThat(actualStoredWithdrawal.id).isNotBlank
    }

    @Test
    fun offchainCancelWithdrawalTest() {
        val storedWithdrawal = offchainStoreWithdrawal(withdrawal)
        offchainCancelWithdrawal(storedWithdrawal.id)
    }

    @Test
    fun offchainCompleteWithdrawalTest() {
        val transferBody = TransferEthErc20(
            fromPrivateKey = generateEthPrivateKey(true, TESTNET_XPUB_OF_MNEM_15, 0),
            to = faucetAddressOf0,
            nonce = ethGetTransactionsCount(faucetAddressOf0),
            amount = BigDecimal.ZERO,
            currency = Currency.ETH,
        )
        val storedWithdrawal = offchainStoreWithdrawal(withdrawal)
        val tx = sendEthOrErc20Transaction(testnet = true, body = transferBody)
        offchainCompleteWithdrawal(storedWithdrawal.id, tx.txId)
    }
}
