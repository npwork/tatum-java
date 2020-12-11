package io.tatum.model.request;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Optional;

public class OrderBookRequest {

    @NotEmpty
    private TradeType type;

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    @Size(max = 38)
    private String price;

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    @Size(max = 38)
    private String amount;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    @Pattern(regexp = "^[A-a-zZ0-9_\\-]+\\/[A-Za-z0-9_\\-]+$")
    @Size(min = 3, max = 30)
    private String pair;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min = 24, max = 24)
    private String currency1AccountId;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    @Size(min = 24, max = 24)
    private String currency2AccountId;

    @NotEmpty
    @Pattern(regexp = "\\d+")
    @Min(0)
    @Max(100)
    private Optional<BigDecimal> fee;

    @NotEmpty
    @Size(min = 24, max = 24)
    private Optional<String> feeAccountId;

    public TradeType getType() {
        return type;
    }

    public void setType(TradeType type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPair() {
        return pair;
    }

    public void setPair(String pair) {
        this.pair = pair;
    }

    public String getCurrency1AccountId() {
        return currency1AccountId;
    }

    public void setCurrency1AccountId(String currency1AccountId) {
        this.currency1AccountId = currency1AccountId;
    }

    public String getCurrency2AccountId() {
        return currency2AccountId;
    }

    public void setCurrency2AccountId(String currency2AccountId) {
        this.currency2AccountId = currency2AccountId;
    }

    public Optional<BigDecimal> getFee() {
        return fee;
    }

    public void setFee(Optional<BigDecimal> fee) {
        this.fee = fee;
    }

    public Optional<String> getFeeAccountId() {
        return feeAccountId;
    }

    public void setFeeAccountId(Optional<String> feeAccountId) {
        this.feeAccountId = feeAccountId;
    }
}
