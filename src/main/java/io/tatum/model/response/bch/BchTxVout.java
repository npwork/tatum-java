package io.tatum.model.response.bch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Bch tx vout.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BchTxVout {

    private BigDecimal value;
    private long n;
    private BchTxScriptPubKey scriptPubKey;

}
