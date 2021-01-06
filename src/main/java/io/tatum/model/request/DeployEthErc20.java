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
 * The type Deploy eth erc 20.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class DeployEthErc20 {

    @NotEmpty
    @Size(min = 1, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$")
    private String name;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String symbol;

    @NotEmpty
    @Size(min = 42, max = 42)
    private String address;

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String supply;

    @Min(1)
    @Max(30)
    private int digits;

    @NotEmpty
    @Size(min = 66, max = 66)
    private String fromPrivateKey;

    @Min(0)
    private BigInteger nonce;

    @Valid
    private Fee fee;
}
