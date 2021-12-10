package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Vet tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetTx {
    private String id;
    private String chainTag;
    private String blockRef;
    private Long expiration;
    private VetTxClauses[] clauses;
    private BigDecimal gasPriceCoef;
    private BigDecimal gas;
    private String origin;
    private String nonce;
    private BigDecimal size;
    private VetTxMeta meta;
    private Long blockNumber;
}
