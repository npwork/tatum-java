package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type Create withdrawal.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class CreateWithdrawal {

    @NotEmpty
    @Size(min = 24, max = 24)
    private String senderAccountId;

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String address;

    @NotEmpty
    @Size(max = 38)
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String fee;

    private boolean compliant;

    @Size(min = 1, max = 100)
    private String paymentId;

    @Size(min = 1, max = 500)
    private String senderNote;

    private String[] multipleAmounts;

    @Size(max = 64)
    @Pattern(regexp = "^[ -~]{1,64}$")
    private String attr;
}
