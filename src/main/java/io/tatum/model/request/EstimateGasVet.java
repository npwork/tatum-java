package io.tatum.model.request;

import javax.validation.constraints.*;
import java.util.Optional;

public class EstimateGasVet {

    @NotEmpty
    @Size(min = 66, max = 66)
    private String from;

    @NotEmpty
    @Size(min = 42, max = 42)
    private String to;

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp="^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String value;

//    @IsOptional()
    @Size(max = 10000)
    private Optional<String> data;

//    @IsOptional()
    @Min(0)
    @PositiveOrZero
    private Optional<Integer> nonce;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Optional<String> getData() {
        return data;
    }

    public void setData(Optional<String> data) {
        this.data = data;
    }

    public Optional<Integer> getNonce() {
        return nonce;
    }

    public void setNonce(Optional<Integer> nonce) {
        this.nonce = nonce;
    }
}
