package io.tatum.model.response.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

/**
 * The type Eth block.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthBlock {

    private BigDecimal difficulty;
    private String extraData;
    private BigDecimal gasLimit;
    private BigDecimal gasUsed;
    private String hash;
    private String logsBloom;
    private String miner;
    private String mixHash;
    private String nonce;
    private BigDecimal number;
    private String parentHash;
    private String receiptsRoot;
    private String sha3Uncles;
    private BigDecimal size;
    private String stateRoot;
    private long timestamp;
    private BigDecimal totalDifficulty;
    private EthTx[] transactions;
    private String transactionsRoot;

}
