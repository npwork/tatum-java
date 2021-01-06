package io.tatum.model.request;

import io.tatum.model.request.transaction.Fee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigInteger;

/**
 * The type Transfer custom erc 20.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferCustomErc20 {

    @NotEmpty
    @Size(min = 66, max = 66)
    private String fromPrivateKey;

    @NotEmpty
    @Size(min = 42, max = 42)
    private String to;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @NotEmpty
    @Size(min = 42, max = 42)
    private String contractAddress;

    @Valid
    private Fee fee;

    @Min(1)
    @Max(30)
    private int digits;

    @Min(0)
    private BigInteger nonce;
}
