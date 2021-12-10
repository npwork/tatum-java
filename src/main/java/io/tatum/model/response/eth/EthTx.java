package io.tatum.model.response.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * The type Eth tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthTx {

    private String blockHash;
    private Boolean status;
    private BigDecimal blockNumber;
    private String from;
    private BigDecimal gas;
    private BigDecimal gasPrice;
    private String transactionHash;
    private String input;
    private BigDecimal nonce;
    private String to;
    private BigDecimal transactionIndex;
    private BigDecimal value;
    private BigDecimal gasUsed;
    private BigDecimal cumulativeGasUsed;
    private String contractAddress;
    private List<EthTxLogs> logs;
}
