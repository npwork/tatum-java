package io.tatum.model.response.tron;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class TronTransaction {

    private Ret[] ret;
    private String[] signature;
    private String txID;
    private long netFee;
    private long netUsage;
    private long energyFee;
    private long energyUsage;
    private long energyUsageTotal;
    private Object[] internalTransactions;
    private RawData rawData;
}
