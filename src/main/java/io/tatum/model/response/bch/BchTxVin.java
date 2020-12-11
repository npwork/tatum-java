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
public class BchTxVin implements IBchTxVin {

    private String txid;
    private BigDecimal vout;
    private IBchTxScriptSig scriptSig;
    private String coinbase;
    private BigDecimal sequence;

}
