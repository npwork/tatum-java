package blockchain

import TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1
import io.mockk.every
import io.mockk.mockkObject
import io.tatum.blockchain.ethGetAccountBalance
import io.tatum.blockchain.ethGetAccountErc20Balance
import io.tatum.blockchain.ethGetAccountTransactions
import io.tatum.blockchain.ethGetBlock
import io.tatum.blockchain.ethGetCurrentBlock
import io.tatum.blockchain.ethGetTransaction
import io.tatum.model.response.common.TransactionHash
import io.tatum.model.response.eth.EthBlock
import io.tatum.model.response.eth.EthTx
import io.tatum.transaction.Contract
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.kethereum.model.Address
import java.math.BigDecimal
import java.math.BigInteger
import java.util.function.Consumer

private val expectedEthTxHash = TransactionHash("0xe6fb3d641311b3b099c687af0de94357a061db09140413bfce5563669970ad9b")
private val expectedEthTx = Consumer<EthTx> {
    it.nonce == BigInteger.ZERO
    it.blockHash == "0xc18de26b21a0569039da410ec5f10c29b43dfe4a2576d80f2c25dd5a80946afd"
    it.blockNumber == BigInteger.valueOf(8643880)
    it.input == "0x"
    it.logs?.isEmpty()
    it.transactionHash == expectedEthTxHash.txId
    it.from == "0x9b29f0126b7f5be6585c990a2e564746a55c9872"
    it.to == "0xbd53bf6573321d159453f09e71b053d5dbf5ce31"
}

private val expectedEthBlock = Consumer<EthBlock> {
    it.number == BigInteger.valueOf(6470657)
    it.hash == "0x5d40698ee1b1ec589035f2a39c6162287e9056868cc79d66cfb248ba9f66c3fc"
    it.parentHash == "0xd34aab8a455027086ac54034e68608c84d984401a883b7b5d91f9ae0bbefda15"
    it.nonce == "0xfa1692f52a7ac672"
    it.sha3Uncles == "0x1dcc4de8dec75d7aab85b567b6ccd41ad312451b948a7413f0a142fd40d49347"
    it.logsBloom == "0x042080004210492080800001610060ad9600005bc81502020800000043e302020381a404000100409102040240300641108004000400007000008000c049558055a800000a0001800748900806502004200400108205005418c0218802281a0408060000533210462021050470810a010140102809011814018281115020090201068401847000a04010000c00084062000485640d00020220181150020008589105a41c0880001112034100010882545410240011402a3480050104004c310204000002009490d0012022a040c20c20011020401020140250805100410060008280200008181a220114102800001648640828200c00a94c1003a2060e001000"
    it.transactionsRoot == "0x5990081ef8515d561b50255af03c5d505f7725ddef27405dc67d23bfd0f47704"
    it.stateRoot == "0x32757c92f10c6c5a106c6fb4b9ca3ff301e413a59ca3d0513b4bf98c72efddba"
    it.miner == "0xd8869d9e3d497323561fbca2319a9fc3f6f10c4b"
    it.difficulty == BigInteger.valueOf(3296820833)
    it.totalDifficulty == "23329673338013873"
    it.extraData == "0x"
    it.size == BigInteger.valueOf(15296)
    it.gasLimit == BigInteger.valueOf(8000000)
    it.gasUsed == BigInteger.valueOf(7985124)
    it.timestamp == BigInteger.valueOf(1569600592)
}

/**
 * Tests are preformed on eth testnet (ropsten)
 */
internal class EthereumKtTest {

    @Test
    fun ethGetCurrentBlockTest() {
        val actualLastBlock: BigInteger = ethGetCurrentBlock()

        assertThat(actualLastBlock).isGreaterThan(BigInteger.TEN)
    }

    /**
     * Test block number 6470657
     */
    @Test
    fun ethGetBlockTest() {
        val actualBlock: EthBlock = ethGetBlock("6470657")

        assertThat(actualBlock)
            .satisfies(expectedEthBlock)
            .extracting("transactions")
                .isInstanceOf(List::class.java)
                .isNotNull
    }

    @Test
    fun ethGetAccountBalanceTest() {
        val actualAccountBalance = ethGetAccountBalance(Address(TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1))

        assertThat(actualAccountBalance.balance).isGreaterThan(BigDecimal.ZERO)
    }

    /**
     * For testing purpose Contract address has to be mocked to match actual contract address on Testnet.
     * ERC20 address "0x494394c74bFF7f93C8EB390D4Ab3586Aa2BcAb0C" should have approximately 100000000000000000 coin amounts.
     */
    @Test
    fun ethGetAccountErc20AddressTest() {
        mockkObject(Contract.USDT)
        every { Contract.USDT.address } returns Address("0x494394c74bFF7f93C8EB390D4Ab3586Aa2BcAb0C")

        val actualBalance = ethGetAccountErc20Balance(
            Address("0x811dfbff13adfbc3cf653dcc373c03616d3471c9"),
            Contract.USDT,
        )

        assertThat(actualBalance.balance).isGreaterThan(BigDecimal.TEN)
    }

    @Test
    fun ethGetTransactionTest() {
        val actualTransaction = ethGetTransaction(expectedEthTxHash)

        assertThat(actualTransaction).satisfies(expectedEthTx)
    }

    @Test
    fun ethGetAccountTransactionsTest() {
        val actualTransactions = ethGetAccountTransactions(Address(TESTNET_ETH_ADDRESS_OF_MNEM_15_AT_1))

        assertThat(actualTransactions).filteredOn { it?.transactionHash == expectedEthTxHash.txId }
            .hasOnlyOneElementSatisfying(expectedEthTx)
    }
}
