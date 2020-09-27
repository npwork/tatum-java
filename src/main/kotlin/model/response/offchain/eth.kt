package model.response.offchain

import Currency
import ledger.getAccountById
import model.request.BroadcastWithdrawal
import model.request.TransferEthOffchain
import model.request.Withdrawal
import model.response.common.TatumException
import offchain.offchainBroadcast
import offchain.offchainCancelWithdrawal
import offchain.offchainStoreWithdrawal
import org.kethereum.crypto.toECKeyPair
import org.kethereum.eip155.signViaEIP155
import org.kethereum.extensions.transactions.encodeRLP
import org.kethereum.model.Address
import org.kethereum.model.ChainId
import org.kethereum.model.PrivateKey
import org.kethereum.model.Transaction
import org.komputing.kethereum.erc1450.ERC1450TransactionGenerator
import org.komputing.khex.extensions.toHexString
import org.komputing.khex.model.HexString
import transaction.Contract
import transaction.EthChain
import transaction.ethToWei
import wallet.address.generatePrivateKeyFromMnemonic
import java.math.BigInteger

/**
 * Send Ethereum transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
 * This operation is irreversible.
 * @param testnet mainnet or testnet version
 * @param body content of the transaction to broadcast
 * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
 * @returns transaction id of the transaction in the blockchain
 */
fun sendEthOffchainTransaction(testnet: Boolean, body: TransferEthOffchain) {
    val withdrawalFee = body.fee.gasLimit.multiply(body.fee.gasPrice)

    // withdrawal = {senderAccountId, address, compliant, paymentId, senderNote, nonce}
    val withdrawal = Withdrawal(
        //data = body.data, // data is silenced/lost
        amount = body.amount.toBigDecimal(),
        address = body.to,
        paymentId = body.paymentId,
        compliant = body.compliant,
        senderAccountId = body.senderAccountId,
        fee = withdrawalFee // in wei
    )

    val signedTransaction = prepareEthSignedOffchainTransaction(testnet, body)
    val storedWithdrawal = offchainStoreWithdrawal(withdrawal)
    val broadcastWithdrawal = BroadcastWithdrawal(
        currency = Currency.ETH.name,
        txData = signedTransaction,
        withdrawalId = storedWithdrawal.id,
    )

    try {
        offchainBroadcast(broadcastWithdrawal)
    } catch (e: TatumException) {
        offchainCancelWithdrawal(storedWithdrawal.id)
        throw e
    }
}

/**
 * Sign Ethereum transaction with private keys locally. Nothing is broadcast to the blockchain.
 * @param amount amount to send
 * @param privateKey private key to sign transaction and send funds from
 * @param address recipient address
 * @param currency Ethereum or supported ERC20
 * @param web3 instance of the web3 client
 * @param gasPrice gas price of the blockchain fee
 * @param nonce nonce of the transaction. this is counter of the transactions from given address. should be + 1 from previous one.
 * @returns transaction data to be broadcast to blockchain.
 */
fun prepareEthSignedOffchainTransaction(testnet: Boolean, body: TransferEthOffchain): String {
    val account = getAccountById(body.senderAccountId)
    val currency = Currency.valueOf(account.currency)

    val nonce = body.nonce
    val amountInWei = body.amount.toBigDecimal().ethToWei()
    val toAddress = Address(body.to)

    val gasPrice = body.fee.gasPrice
    val chainId = if (testnet) EthChain.ROPSTEN.id.value else EthChain.ETHEREUM_MAINNET.id.value

    // Create a transaction.
    val transaction = if (currency == Currency.ETH) {
        Transaction().apply {
            to = toAddress
            chain = chainId
            value = amountInWei
            this.nonce = nonce
            this.gasPrice = gasPrice
            gasLimit = body.fee.gasLimit
        }
    } else {
        val contract = Contract.valueOf(currency.name)
        ERC1450TransactionGenerator(contract.address).mint(toAddress, amountInWei).copy(
            value = BigInteger.ZERO, // real value is added in mint fun and sent via input(i.e. data)
            chain = chainId,
            nonce = nonce,
            gasPrice = gasPrice,
            gasLimit = body.fee.gasLimit,
        )
    }

    // Sign just created transaction.
    val chain = ChainId(chainId)
    val fromPrivateKey: String = body.privateKey ?: generatePrivateKeyFromMnemonic(
        currency,
        testnet,
        body.mnemonic!!, // if mnemonic is null at this step, report bug
        body.index!!, // if index is null at this step, report bug
    )
    val sendersPrivateKey = PrivateKey(HexString(fromPrivateKey))
    val signature = transaction.signViaEIP155(sendersPrivateKey.toECKeyPair(), chain)

    return transaction.encodeRLP(signature).toHexString()
}

/**
 * Sign Ethereum custom ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
 * @param amount amount to send
 * @param privateKey private key to sign transaction and send funds from
 * @param address recipient address
 * @param tokenAddress blockchain address of the custom ERC20 token
 * @param web3 instance of the web3 client
 * @param gasPrice gas price of the blockchain fee
 * @param nonce nonce of the transaction. this is counter of the transactions from given address. should be + 1 from previous one.
 * @returns transaction data to be broadcast to blockchain.
 */
fun prepareEthErc20SignedOffchainTransaction(): Unit = TODO("Will be implemented later.")
