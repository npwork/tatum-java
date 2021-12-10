package io.tatum.model.response.vet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Vet tx receipt.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class VetTxReceipt {
    private BigDecimal gasUsed;
    private String gasPayer;
    private String paid;
    private String reward;
    private Boolean reverted;
    private VetTxReceiptMeta meta;
    private VetTxReceiptOutputs[] outputs;
    private Long blockNumber;
    private String blockHash;
    private String transactionHash;
    private String status;
}
