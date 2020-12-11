package io.tatum.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Optional;

public class CreateTransaction {

    @NotEmpty
    @Size(min = 24, max = 24)
    private String senderAccountId;

    @NotEmpty
    @Size(min = 24, max = 24)
    private String recipientAccountId;

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @Size(min = 1, max = 100)
    private Optional<String> paymentId;

    @Size(min = 1, max = 100)
    private Optional<String> transactionCode;

    @Size(min = 1, max = 500)
    private Optional<String> senderNote;

    @Size(min = 1, max = 500)
    private Optional<String> recipientNote;

    @Min(0)
    private Optional<BigDecimal> baseRate;

    private Optional<Boolean> anonymous;

    private Optional<Boolean> compliant;

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(String recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public Optional<BigDecimal> getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Optional<BigDecimal> baseRate) {
        this.baseRate = baseRate;
    }

    public Optional<Boolean> getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Optional<Boolean> anonymous) {
        this.anonymous = anonymous;
    }

    public Optional<Boolean> getCompliant() {
        return compliant;
    }

    public void setCompliant(Optional<Boolean> compliant) {
        this.compliant = compliant;
    }
}