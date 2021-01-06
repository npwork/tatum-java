package io.tatum.model.response.eth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Eth tx logs.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthTxLogs {

    private String address;
    private String[] topic;
    private String data;
    private BigDecimal logIndex;
    private BigDecimal transactionIndex;
    private String transactionHash;

}
