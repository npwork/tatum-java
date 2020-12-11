package io.tatum.model.response.bch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class BchTx implements IBchTx {

    private String txid;
    private BigDecimal version;
    private BigDecimal locktime;
    private IBchTxVin[] vin;
    private IBchTxVout[] vout;

}
