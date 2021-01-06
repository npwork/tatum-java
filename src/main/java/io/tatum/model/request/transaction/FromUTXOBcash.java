package io.tatum.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * The type From utxo bcash.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class FromUTXOBcash extends FromUTXO {

    /**
     * The Value.
     */
    @NotEmpty
    @Pattern(regexp = "\\d+") // number string
    @Min(0)
    public String value;
}
