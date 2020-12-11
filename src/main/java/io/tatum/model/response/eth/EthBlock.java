package io.tatum.model.response.eth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class EthBlock implements IEthBlock {

    private String difficulty;
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
    private BigDecimal timestamp;
    private String totalDifficulty;
    private IEthTx[] transactions;
    private String transactionsRoot;

}
