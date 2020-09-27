package ledger

import TESTNET_XPUB_OF_MNEM_15
import model.request.BlockAmount
import model.request.Country
import model.request.CreateAccount
import model.request.CustomerUpdate
import model.response.ledger.Account
import model.response.ledger.AccountBalance
import model.response.ledger.Blockage
import model.response.ledger.Fiat
import model.response.ledger.IdString
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.function.Consumer
import java.util.stream.Stream

private val ETH_CURRENCY = "ETH"
private val ACCOUNT_CODE = "client1234"
private val EXTENRAL_ID = "idInRandomCompany"

val CUSTOMER = CustomerUpdate(
    customerCountry = Country.SK,
    accountingCurrency = Fiat.EUR,
    providerCountry = Country.SK,
    externalId = EXTENRAL_ID,
)

val PREPARED_ACCOUNT_WITHOUT_BLOCKCHAIN_ADDRESS = CreateAccount(
    currency = ETH_CURRENCY,
    accountCode = ACCOUNT_CODE,
    customer = CUSTOMER,
)

val PREPARED_ACCOUNT_WITH_BLOCKCHAIN_ADDRESS = CreateAccount(
    currency = ETH_CURRENCY,
    xpub = TESTNET_XPUB_OF_MNEM_15,
    accountCode = ACCOUNT_CODE,
    customer = CUSTOMER,
)

private val EXPECTED_ACCOUNT_WITH_BLOCKCHAIN = Consumer<Account> {
    it.id.isNotBlank()
    it.accountCode == ACCOUNT_CODE
    it.xpub == TESTNET_XPUB_OF_MNEM_15
    it.currency == ETH_CURRENCY
}

private val EXPECTED_ACCOUNT_WITHOUT_BLOCKCHAIN = Consumer<Account> {
    it.id.isNotBlank()
    it.accountCode == ACCOUNT_CODE
    it.xpub == null
    it.currency == ETH_CURRENCY
}

private val EXPECTED_CUSTOMER = Consumer<Account> {
    it.customerId?.isNotBlank()
    it.accountingCurrency == Fiat.EUR
}

private val EXPECTED_BALANCE = Consumer<AccountBalance> {
    it.accountBalance == "0"
}

val EXPECTED_BLOCKAGE_WITH_DESC: (String) -> Consumer<Blockage> =
    { desc: String ->
        Consumer<Blockage> {
            it.description == desc
        }
    }

val PREPARED_BLOCK_AMOUNT_AMOUNT: BigDecimal = BigDecimal.valueOf(0.0005)
const val PREPARED_BLOCK_AMOUNT_TYPE = "UNIT_TESTING_BLOCK_TATUM_JAVA"
const val PREPARED_BLOCK_AMOUNT_DESCRIPTION = "Unit test of tatum-java"

private lateinit var TEST_ACCOUNT_WITH_BLOCKCHAIN: Account
private lateinit var TEST_ACCOUNT_WITHOUT_BLOCKCHAIN: Account

fun prepareBlockAmount(desc: String) = BlockAmount(
    PREPARED_BLOCK_AMOUNT_AMOUNT,
    PREPARED_BLOCK_AMOUNT_TYPE,
    desc,
)

private fun createBlockage(desc: String, acc: Account): IdString {
    val amountToBlock = prepareBlockAmount(desc)
    return blockAmount(acc.id, amountToBlock)
}

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class AccountKtTest {

    @BeforeAll
    fun createTestAccount() {
        TEST_ACCOUNT_WITH_BLOCKCHAIN = createAccount(PREPARED_ACCOUNT_WITH_BLOCKCHAIN_ADDRESS)
        println("New testing account with assigned blockchain address has an id ${TEST_ACCOUNT_WITH_BLOCKCHAIN.id}")

        TEST_ACCOUNT_WITHOUT_BLOCKCHAIN = createAccount(PREPARED_ACCOUNT_WITHOUT_BLOCKCHAIN_ADDRESS)
        println("New testing account without assigned blockchain address has an id ${TEST_ACCOUNT_WITHOUT_BLOCKCHAIN.id}")
    }

    private companion object {
        @JvmStatic
        fun accountArguments(): Stream<Arguments> = Stream.of(
            Arguments.of(TEST_ACCOUNT_WITH_BLOCKCHAIN, EXPECTED_ACCOUNT_WITH_BLOCKCHAIN),
            Arguments.of(TEST_ACCOUNT_WITHOUT_BLOCKCHAIN, EXPECTED_ACCOUNT_WITHOUT_BLOCKCHAIN),
        )
    }

    @ParameterizedTest
    @MethodSource("accountArguments")
    fun `create account with assigned blockchain address`(
        testAccount: Account,
        expectedAccount: Consumer<Account>,
    ) {
        val actualCreatedAccount = testAccount

        assertThat(actualCreatedAccount).satisfies(expectedAccount)
    }

    @ParameterizedTest
    @MethodSource("accountArguments")
    fun getAccountByIdTest(
        testAccount: Account,
        expectedAccount: Consumer<Account>,
    ) {
        val createdAccountId = testAccount.id
        val actualAccount = getAccountById(createdAccountId)

        assertThat(actualAccount).satisfies(expectedAccount)
        assertThat(actualAccount.id).isEqualTo(createdAccountId)
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Blockage {

        private fun accountArguments() = AccountKtTest.accountArguments()

        @ParameterizedTest
        @MethodSource("accountArguments")
        fun blockAmountTest(
            testAccount: Account,
        ) {
            val blockageDescription = "blockAmountTest"
            val actualBlockageId = createBlockage(blockageDescription, testAccount).id

            assertThat(actualBlockageId).isNotBlank
        }

        @ParameterizedTest
        @MethodSource("accountArguments")
        fun getBlockedAmountsByAccountIdTest(
            testAccount: Account,
        ) {
            val blockageDescription = "getBlockedAmountsByAccountIdTest"
            createBlockage(blockageDescription, testAccount)

            val actualBlockedAmounts = getBlockedAmountsByAccountId(testAccount.id)

            assertThat(actualBlockedAmounts)
                .hasOnlyOneElementSatisfying(EXPECTED_BLOCKAGE_WITH_DESC(blockageDescription))
        }

        @ParameterizedTest
        @MethodSource("accountArguments")
        fun deleteBlockedAmountTest(
            testAccount: Account,
        ) {
            val blockageDescription = "deleteBlockedAmountTest"
            val blockedAmountId = createBlockage(blockageDescription, testAccount).id

            deleteBlockedAmount(blockedAmountId)
            val actualBlockedAmounts = getBlockedAmountsByAccountId(testAccount.id)

            assertThat(actualBlockedAmounts)
                .noneSatisfy(EXPECTED_BLOCKAGE_WITH_DESC(blockageDescription))
        }

        @ParameterizedTest
        @MethodSource("accountArguments")
        fun deleteBlockedAmountForAccountTest(
            testAccount: Account,
        ) {
            val blockageDescription = "deleteBlockedAmountForAccountTest"
            createBlockage(blockageDescription, testAccount).id.also { println("Blockage id: $it") }

            deleteBlockedAmountForAccount(testAccount.id)
            val actualBlockedAmounts = getBlockedAmountsByAccountId(testAccount.id)

            assertThat(actualBlockedAmounts).isEmpty()
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeActivation {

        private fun accountArguments() = AccountKtTest.accountArguments()

        @ParameterizedTest
        @MethodSource("accountArguments")
        fun reActivateAccountTest(
            testAccount: Account,
        ) {
            assertThat(getAccountById(testAccount.id).active).isTrue

            deactivateAccount(testAccount.id)
            // Deactivated account is not returned, 430 instead.
            // assertThat(getAccountById(TEST_ACCOUNT.id).active).isFalse

            activateAccount(testAccount.id)
            assertThat(getAccountById(testAccount.id).active).isTrue
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UnFreeze {

        private fun accountArguments() = AccountKtTest.accountArguments()

        @ParameterizedTest
        @MethodSource("accountArguments")
        fun `Freeze and unfreeze the account`(
            testAccount: Account,
        ) {
            assertThat(getAccountById(testAccount.id).frozen).isFalse

            freezeAccount(testAccount.id)
            assertThat(getAccountById(testAccount.id).frozen).isTrue

            unfreezeAccount(testAccount.id)
            assertThat(getAccountById(testAccount.id).frozen).isFalse
        }
    }

    @ParameterizedTest
    @MethodSource("accountArguments")
    fun getAccountsByCustomerIdTest(
        testAccount: Account,
        expectedAccount: Consumer<Account>,
    ) {
        val actualAccount = testAccount.customerId?.let { getAccountsByCustomerId(it) }

        assertThat(actualAccount).allSatisfy(expectedAccount).allSatisfy(EXPECTED_CUSTOMER)
    }

    @Test
    fun getAllAccountsTest() {
        val actualAccounts = getAllAccounts()

        assertThat(actualAccounts).isNotEmpty.allSatisfy {
            it.id.isNotBlank()
            it.accountCode == ACCOUNT_CODE
            it.currency == ETH_CURRENCY
        }
    }

    @ParameterizedTest
    @MethodSource("accountArguments")
    fun getAccountBalanceTest(
        testAccount: Account,
    ) {
        val actualBalance = getAccountBalance(testAccount.id)

        assertThat(actualBalance).satisfies(EXPECTED_BALANCE)
    }
}

const val ACCOUNT_NUMBER = "5"

val EXPECTED_CREATE_ACCOUNT = Consumer<CreateAccount> {
    it.currency == ETH_CURRENCY
    it.xpub == TESTNET_XPUB_OF_MNEM_15
    it.accountCode == ACCOUNT_CODE
    it.accountNumber == ACCOUNT_NUMBER
}

internal class InitValidationTest {

    /**
     * Test BlockAmount validation.
     */
    @Nested
    inner class InitBlockAmount {

        @Test
        fun `create BlockAmount with valid params`() {
            val actualThrow = BlockAmount(
                "1".repeat(38).toBigDecimal(),
                "VALID_TYPE",
                "Valid description",
            )

            assertThat(actualThrow)
                .hasFieldOrPropertyWithValue("amount", "1".repeat(38))
        }

        @Test
        fun `amount with too many digits`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                BlockAmount(
                    "1".repeat(39).toBigDecimal(),
                    "VALID_TYPE",
                    "Valid description",
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `type is too long`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                BlockAmount(
                    BigDecimal.valueOf(3548978946.6163),
                    "VALID_TYPE".padStart(101, 'X'),
                    "Valid description",
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `type is empty`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                BlockAmount(
                    BigDecimal.valueOf(3548978946.6163),
                    "",
                    "Valid description",
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `description is too long`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                BlockAmount(
                    BigDecimal.valueOf(3548978946.6163),
                    "VALID_TYPE",
                    "Valid description".padStart(301, 'X'),
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `description is empty`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                BlockAmount(
                    BigDecimal.valueOf(3548978946.6163),
                    "VALID_TYPE",
                    "",
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }
    }

    /**
     * Test CreateAccount validation.
     */
    @Nested
    inner class InitCreateAccountTest {

        @Test
        fun `create CreateAccount only with currency`() {
            val actualCreateAccount = CreateAccount("ETH")

            assertThat(actualCreateAccount).isInstanceOf(CreateAccount::class.java)
        }

        @Test
        fun `create CreateAccount with valid params`() {
            val actualCreateAccount = CreateAccount(
                currency = ETH_CURRENCY,
                xpub = TESTNET_XPUB_OF_MNEM_15,
                accountCode = ACCOUNT_CODE,
                accountNumber = ACCOUNT_NUMBER,
            )

            assertThat(actualCreateAccount).satisfies(EXPECTED_CREATE_ACCOUNT)
        }

        @Test
        fun `currency is too short`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount("E")
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `currency is too long`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount("E".repeat(41))
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `xpub is too long`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount(
                    currency = ETH_CURRENCY,
                    xpub = TESTNET_XPUB_OF_MNEM_15.padStart(151, 'X'),
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `accountCode is empty`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount(
                    currency = ETH_CURRENCY,
                    accountCode = "",
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `accountCode is too long`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount(
                    currency = ETH_CURRENCY,
                    accountCode = ACCOUNT_CODE.padStart(51, 'X'),
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `accountNumber is empty`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount(
                    currency = ETH_CURRENCY,
                    accountNumber = "",
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `accountNumber is too long`() {
            val actualThrow: Throwable = Assertions.catchThrowable {
                CreateAccount(
                    currency = ETH_CURRENCY,
                    accountNumber = ACCOUNT_NUMBER.padStart(31, 'X'),
                )
            }

            assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
        }
    }
}
