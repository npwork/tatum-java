package io.tatum.transaction.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vechain.thorclient.core.model.blockchain.RawClause;
import com.vechain.thorclient.core.model.clients.TransactionReserved;
import com.vechain.thorclient.utils.RLPUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionJSON {
    private int chainTag; // 1 bytes
    private String blockRef; //8 bytes
    private String expiration; //4 bytes

    // 1-255 used baseprice 255 used 2x base price
    private byte gasPriceCoef;

    // gas limit the max gas for VET 21000 for VTHO 80000
    private String gas;//64 bytes
    private String dependsOn;
    private String nonce;    //8 bytes
    private String signature;

    private String to;
    private String amount;
}
