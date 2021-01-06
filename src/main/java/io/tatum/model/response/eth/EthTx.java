package io.tatum.model.response.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Eth tx.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthTx {

    private String blockHash;
    private Boolean status;
    private BigDecimal blockNumber;
    private String from;
    private BigDecimal gas;
    private String gasPrice;
    private String transactionHash;
    private String input;
    private BigDecimal nonce;
    private String to;
    private BigDecimal transactionIndex;
    private String value;
    private BigDecimal gasUsed;
    private BigDecimal cumulativeGasUsed;
    private String contractAddress;
    private EthTxLogs[] logs;

}
