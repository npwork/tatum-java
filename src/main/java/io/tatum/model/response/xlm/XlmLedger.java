package io.tatum.model.response.xlm;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class XlmLedger {
    private String id;

    @JsonProperty("paging_token")
    private Long pagingToken;

    private String hash;

    @JsonProperty("prev_hash")
    private String prevHash;

    private Long sequence;

    @JsonProperty("successful_transaction_count")
    private Long successfulTransactionCount;

    @JsonProperty("failed_transaction_count")
    private Long failedTransactionCount;

    @JsonProperty("operation_count")
    private Long operationCount;

    @JsonProperty("tx_set_operation_count")
    private Long txSetOperationCount;

    // @TODO - parse date
    @JsonProperty("closed_at")
    private String closedAt;

    @JsonProperty("total_coins")
    private BigDecimal totalCoins;

    @JsonProperty("fee_pool")
    private BigDecimal feePool;

    @JsonProperty("base_fee_in_stroops")
    private BigDecimal baseFeeInStroops;

    @JsonProperty("base_reserve_in_stroops")
    private BigDecimal baseReserveInStroops;

    @JsonProperty("max_tx_set_size")
    private BigDecimal maxTxSetSize;

    @JsonProperty("protocol_version")
    private Long protocolVersion;

    @JsonProperty("header_xdr")
    private String headerXdr;
}
