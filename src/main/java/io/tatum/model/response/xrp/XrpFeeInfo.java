package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrpFeeInfo {
    @JsonProperty("current_ledger_size")
    private long currentLedgerSize;

    @JsonProperty("current_queue_size")
    private long currentQueueSize;

    @JsonProperty("expected_ledger_size")
    private long expectedLedgerSize;

    @JsonProperty("ledger_current_index")
    private long ledgerCurrentIndex;

    @JsonProperty("max_queue_size")
    private long maxQueueSize;

    private XrpInfoDrops drops;

    public BigDecimal getFee() {
        return drops.getBaseFee();
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
class XrpInfoDrops {
    @JsonProperty("base_fee")
    private BigDecimal baseFee;

    @JsonProperty("median_fee")
    private BigDecimal medianFee;

    @JsonProperty("minimum_fee")
    private BigDecimal minimumFee;
    @JsonProperty("open_ledger_fee")
    private BigDecimal openLedgerFee;
}
