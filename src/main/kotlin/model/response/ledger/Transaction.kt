package model.response.ledger

/**
 * @property accountId Source account - source of transaction(s)
 * @property amount Amount in account's currency
 * @property anonymous Whether the transaction is anonymous. If true, counter account owner does not see source account.
 * @property counterAccountId Counter account - transaction(s) destination account. In case of blockchain recipient, this is addess of blockchain account.
 * @property currency Transaction currency
 * @property date Time in UTC of transaction.
 * @property marketValue List of market values of given transaction with all supported base pairs.
 * @property operationType Type of operation.
 * @property paymentId Payment ID defined in payment order by sender.
 * @property attr Present only for operationType WITHDRAWAL and XLM / XRP based accounts it represents message or destinationTag of the recipient, if present.
 * @property address For operationType DEPOSIT it represents address, on which was deposit credited for the account.
 * @property recipientNote Note visible for both sender and recipient.
 * @property reference Transaction internal reference - unique identifier within Tatum ledger. In order of failure, use this value to search for problems.
 * @property txId For operationType DEPOSIT, BLOCKCHAIN_TRANSACTION it represents transaction id, for which deposit occurred.
 * @property senderNote Note visible for sender.
 * @property transactionCode For bookkeeping to distinct transaction purpose.
 * @property transactionType Type of payment.
 */
class Transaction(
    val accountId: String,
    val amount: String,
    val anonymous: Boolean,
    val counterAccountId: String? = null,
    val currency: String,
    val date: String,
    val marketValue: Array<MarketValue>,
    operationType: String,
    val paymentId: String? = null,
    val attr: String? = null,
    val address: String? = null,
    val recipientNote: String? = null,
    val reference: String,
    val txId: String? = null,
    val senderNote: String? = null,
    val transactionCode: String? = null,
    transactionType: String,
) {
    val operationType: OperationType = OperationType.valueOf(operationType)
    val transactionType: TransactionType = TransactionType.valueOf(transactionType)
}
