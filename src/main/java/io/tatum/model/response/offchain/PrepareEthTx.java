package io.tatum.model.response.offchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * The type Prepare eth tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class PrepareEthTx {
    private String tx;
    private BigInteger gasLimit;
}
