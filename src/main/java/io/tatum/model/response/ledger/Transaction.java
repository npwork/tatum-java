package io.tatum.model.response.ledger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Transaction {
    /**
     * Source account - source of transaction(s)
     *
     * @type {string}
     * @memberof Transaction
     */
    private String accountId;

    /**
     * Amount in account's currency
     *
     * @type {string}
     * @memberof Transaction
     */
    private String amount;

    /**
     * Whether the transaction is anonymous. If true, counter account owner does not see source account.
     *
     * @type {boolean}
     * @memberof Transaction
     */
    private Boolean anonymous;

    /**
     * Counter account - transaction(s) destination account. In case of blockchain recipient, this is addess of blockchain account.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String counterAccountId;

    /**
     * Transaction currency
     *
     * @type {string}
     * @memberof Transaction
     */
    private String currency;

    /**
     * Time in UTC of transaction.
     *
     * @type {number}
     * @memberof Transaction
     */
    private BigDecimal created;

    /**
     * List of market values of given transaction with all supported base pairs.
     *
     * @type {Array<MarketValue}
     * @memberof Transaction
     */
    private MarketValue[] marketValue;

    /**
     * Type of operation.
     *
     * @type {string}
     * @memberof Transaction
     */
    private OperationType operationType;

    /**
     * Payment ID defined in payment order by sender.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String paymentId;

    /**
     * Present only for operationType WITHDRAWAL and XLM / XRP based accounts it represents message or destinationTag of the recipient, if present.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String attr;

    /**
     * For operationType DEPOSIT it represents address, on which was deposit credited for the account.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String address;

    /**
     * Note visible for both sender and recipient.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String recipientNote;

    /**
     * Transaction internal reference - unique identifier within Tatum ledger. In order of failure, use this value to search for problems.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String reference;

    /**
     * For operationType DEPOSIT, BLOCKCHAIN_TRANSACTION it represents transaction id, for which deposit occured.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String txId;

    /**
     * Note visible for sender.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String senderNote;

    /**
     * For bookkeeping to distinct transaction purpose.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String transactionCode;

    /**
     * Type of payment.
     *
     * @type {string}
     * @memberof Transaction
     */
    private TransactionType transactionType;

}
