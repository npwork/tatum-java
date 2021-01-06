package io.tatum.model.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * The type From utxo.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class FromUTXO {

    @NotEmpty
    @Size(min = 64, max = 64)
    private String txHash;

    @Min(0)
    @DecimalMax("4294967295")
    private BigDecimal index;

    @NotEmpty
    @Size(min = 52, max = 52)
    private String privateKey;
}
