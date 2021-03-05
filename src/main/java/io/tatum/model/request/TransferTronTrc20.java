package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferTronTrc20 {

    @Size(min=64, max=64)
    private String fromPrivateKey;

    @Size(min=34, max=34)
    private String from;

    @Size(min=36, max=36)
    private String signatureId;

    @NotEmpty
    @Size(min=34, max=34)
    private String to;

    @NotEmpty
    @Size(min=34, max=34)
    private String tokenAddress;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @NotNull
    @Min(0)
    private long feeLimit;
}
