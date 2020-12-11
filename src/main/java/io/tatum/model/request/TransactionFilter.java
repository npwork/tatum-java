package io.tatum.model.request;

import com.google.common.base.Optional;
import io.tatum.model.response.ledger.OperationType;
import io.tatum.model.response.ledger.TransactionType;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TransactionFilter {

    @Size(min = 1, max = 50)
    private Optional<String> id;

    @Min(0)
    private Optional<BigDecimal> from;

    @Min(0)
    private Optional<BigDecimal> to;

    @Size(min = 1, max = 50)
    private Optional<String> account;

    @Size(min = 1, max = 50)
    private Optional<String> counterAccount;

    @Size(min = 1, max = 50)
    private Optional<String> currency;

    @Size(min = 1, max = 100)
    private Optional<String> paymentId;

    @Size(min = 1, max = 100)
    private Optional<String> transactionCode;

    @Size(min = 1, max = 500)
    private Optional<String> senderNote;

    @Size(min = 1, max = 500)
    private Optional<String> recipientNote;

    private Optional<OperationType> opType;

    private Optional<TransactionType> transactionType;

    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public Optional<BigDecimal> getFrom() {
        return from;
    }

    public void setFrom(Optional<BigDecimal> from) {
        this.from = from;
    }

    public Optional<BigDecimal> getTo() {
        return to;
    }

    public void setTo(Optional<BigDecimal> to) {
        this.to = to;
    }

    public Optional<String> getAccount() {
        return account;
    }

    public void setAccount(Optional<String> account) {
        this.account = account;
    }

    public Optional<String> getCounterAccount() {
        return counterAccount;
    }

    public void setCounterAccount(Optional<String> counterAccount) {
        this.counterAccount = counterAccount;
    }

    public Optional<String> getCurrency() {
        return currency;
    }

    public void setCurrency(Optional<String> currency) {
        this.currency = currency;
    }

    public Optional<String> getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Optional<String> paymentId) {
        this.paymentId = paymentId;
    }

    public Optional<String> getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(Optional<String> transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Optional<String> getSenderNote() {
        return senderNote;
    }

    public void setSenderNote(Optional<String> senderNote) {
        this.senderNote = senderNote;
    }

    public Optional<String> getRecipientNote() {
        return recipientNote;
    }

    public void setRecipientNote(Optional<String> recipientNote) {
        this.recipientNote = recipientNote;
    }

    public Optional<OperationType> getOpType() {
        return opType;
    }

    public void setOpType(Optional<OperationType> opType) {
        this.opType = opType;
    }

    public Optional<TransactionType> getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Optional<TransactionType> transactionType) {
        this.transactionType = transactionType;
    }
}