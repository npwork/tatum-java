package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigInteger;

/**
 * The type Base transfer eth erc 20 offchain.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BaseTransferEthErc20Offchain {

    @NotEmpty
    @Size(min=24, max=24)
    private String senderAccountId;

    @NotEmpty
    @Size(min=42, max=42)
    private String address;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    private boolean compliant;

    @Size(min=1, max=100)
    private String paymentId;

    @Size(min=1, max=500)
    private String senderNote;

    @Min(0)
    private BigInteger nonce;

    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$") // number string
    private String gasPrice;

    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$") // number string
    private String gasLimit;
}
