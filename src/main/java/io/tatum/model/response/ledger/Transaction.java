package io.tatum.model.response.ledger;

import java.math.BigDecimal;
import java.util.Optional;

public class Transaction {
    /**
     * Source account - source of transaction(s)
     *
     * @type {string}
     * @memberof Transaction
     */
    private String accountId;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * Amount in account's currency
     *
     * @type {string}
     * @memberof Transaction
     */
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Whether the transaction is anonymous. If true, counter account owner does not see source account.
     *
     * @type {boolean}
     * @memberof Transaction
     */
    private Boolean anonymous;

    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * Counter account - transaction(s) destination account. In case of blockchain recipient, this is addess of blockchain account.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> counterAccountId;

    public Optional<String> getCounterAccountId() {
        return counterAccountId;
    }

    public void setCounterAccountId(Optional<String> counterAccountId) {
        this.counterAccountId = counterAccountId;
    }

    /**
     * Transaction currency
     *
     * @type {string}
     * @memberof Transaction
     */
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Time in UTC of transaction.
     *
     * @type {number}
     * @memberof Transaction
     */
    private BigDecimal created;

    public BigDecimal getCreated() {
        return created;
    }

    public void setCreated(BigDecimal created) {
        this.created = created;
    }

    /**
     * List of market values of given transaction with all supported base pairs.
     *
     * @type {Array<MarketValue>}
     * @memberof Transaction
     */
    private MarketValue[] marketValue;

    public MarketValue[] getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(MarketValue[] marketValue) {
        this.marketValue = marketValue;
    }

    /**
     * Type of operation.
     *
     * @type {string}
     * @memberof Transaction
     */
    private OperationType operationType;

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    /**
     * Payment ID defined in payment order by sender.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> paymentId;

    public Optional<String> getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Optional<String> paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Present only for operationType WITHDRAWAL and XLM / XRP based accounts it represents message or destinationTag of the recipient, if present.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> attr;

    public Optional<String> getAttr() {
        return attr;
    }

    public void setAttr(Optional<String> attr) {
        this.attr = attr;
    }

    /**
     * For operationType DEPOSIT it represents address, on which was deposit credited for the account.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> address;

    public Optional<String> getAddress() {
        return address;
    }

    public void setAddress(Optional<String> address) {
        this.address = address;
    }

    /**
     * Note visible for both sender and recipient.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> recipientNote;

    public Optional<String> getRecipientNote() {
        return recipientNote;
    }

    public void setRecipientNote(Optional<String> recipientNote) {
        this.recipientNote = recipientNote;
    }

    /**
     * Transaction internal reference - unique identifier within Tatum ledger. In order of failure, use this value to search for problems.
     *
     * @type {string}
     * @memberof Transaction
     */
    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * For operationType DEPOSIT, BLOCKCHAIN_TRANSACTION it represents transaction id, for which deposit occured.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> txId;

    public Optional<String> getTxId() {
        return txId;
    }

    public void setTxId(Optional<String> txId) {
        this.txId = txId;
    }

    /**
     * Note visible for sender.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> senderNote;

    public Optional<String> getSenderNote() {
        return senderNote;
    }

    public void setSenderNote(Optional<String> senderNote) {
        this.senderNote = senderNote;
    }

    /**
     * For bookkeeping to distinct transaction purpose.
     *
     * @type {string}
     * @memberof Transaction
     */
    private Optional<String> transactionCode;

    public Optional<String> getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(Optional<String> transactionCode) {
        this.transactionCode = transactionCode;
    }

    /**
     * Type of payment.
     *
     * @type {string}
     * @memberof Transaction
     */
    private TransactionType transactionType;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}