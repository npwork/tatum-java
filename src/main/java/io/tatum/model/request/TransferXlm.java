package io.tatum.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * The type Transfer xlm.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class TransferXlm {

    @NotEmpty
    @Size(min=56, max=56)
    private String fromSecret;

    @NotEmpty
    @Size(min=56, max=56)
    private String to;

    @NotEmpty
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    private boolean initialize;

    @Size(max=64)
    @Pattern(regexp = "^[ -~]{0,64}$")
    private String message;
}
