package io.tatum.transaction

import io.tatum.Currency
import io.tatum.blockchain.TransactionalPayload
import io.tatum.blockchain.ethBroadcast
import io.tatum.model.request.Fee
import io.tatum.model.request.TransferEthErc20
import io.tatum.model.response.common.TransactionHash
import org.kethereum.crypto.toECKeyPair
import org.kethereum.eip155.signViaEIP155
import org.kethereum.extensions.transactions.encodeRLP
import org.kethereum.model.ChainId
import org.kethereum.model.PrivateKey
import org.kethereum.model.Transaction
import org.komputing.kethereum.erc1450.ERC1450TransactionGenerator
import org.komputing.khex.extensions.toHexString
import org.komputing.khex.model.HexString
import java.math.BigInteger

/**
 * Sign Ethereum or supported ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
 * @param testnet mainnet or testnet version
 * @param body content of the transaction to broadcast
 * @returns transaction data to be broadcast to blockchain.
 */
fun prepareEthOrErc20SignedTransaction(testnet: Boolean, body: TransferEthErc20): String {
    val to = body.to
    val amountInWei = body.amount.ethToWei()
    val currency = body.currency
    val fee: Fee = body.fee
    val nonce = body.nonce // fun signViaEIP155 needs nonce to be non-null

    val chainId = if (testnet) EthChain.ROPSTEN.id.value else EthChain.ETHEREUM_MAINNET.id.value

    // Create a transaction.
    val transaction = if (currency == Currency.ETH) {
        Transaction().apply {
            this.to = to
            chain = chainId
            value = amountInWei
            input = body.input
            this.nonce = nonce
            gasPrice = fee.gasPrice
            gasLimit = fee.gasLimit
        }
    } else {
        val contract = Contract.valueOf(currency.name)
        ERC1450TransactionGenerator(contract.address).mint(to, amountInWei).copy(
            value = BigInteger.ZERO, // real value is added in mint fun and sent via input(i.e. data)
            chain = chainId,
            nonce = nonce,
            gasPrice = fee.gasPrice,
            gasLimit = fee.gasLimit,
        )
    }

    // Sign just created transaction.
    val chain = ChainId(chainId)
    val sendersPrivateKey = PrivateKey(HexString(body.fromPrivateKey))
    val signature = transaction.signViaEIP155(sendersPrivateKey.toECKeyPair(), chain)

    return transaction.encodeRLP(signature).toHexString()
}

/**
 * Send Ethereum or supported ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
 * This operation is irreversible.
 * @param testnet mainnet or testnet version
 * @param body content of the transaction to broadcast
 * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
 * @returns transaction id of the transaction in the blockchain
 */
fun sendEthOrErc20Transaction(testnet: Boolean, body: TransferEthErc20): TransactionHash {
    val signedTransaction = prepareEthOrErc20SignedTransaction(testnet, body)
    val payload = TransactionalPayload(signedTransaction)
    return ethBroadcast(payload)
}
