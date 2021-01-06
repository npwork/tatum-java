package io.tatum.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * The type Fee.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Fee {

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp="^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String gasLimit;

    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Pattern(regexp="^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String gasPrice;
}
