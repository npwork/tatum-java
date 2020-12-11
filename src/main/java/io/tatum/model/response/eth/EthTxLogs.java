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
public class EthTxLogs implements IEthTxLogs {

    private String address;
    private String[] topic;
    private String data;
    private BigDecimal logIndex;
    private BigDecimal transactionIndex;
    private String transactionHash;

}
