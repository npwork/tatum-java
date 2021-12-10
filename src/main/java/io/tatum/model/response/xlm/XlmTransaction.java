package io.tatum.model.response.xlm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class XlmTransaction {
    private String id;

    @JsonProperty("paging_token")
    private String pagingToken;

    private Boolean successful;

    private String hash;

    // @TODO - parse date
    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("source_account")
    private String sourceAccount;

    @JsonProperty("source_account_sequence")
    private Long sourceAccountSequence;

    @JsonProperty("fee_account")
    private String feeAccount;

    @JsonProperty("fee_charged")
    private BigDecimal feeCharged;

    @JsonProperty("max_fee")
    private BigDecimal maxFee;

    @JsonProperty("operation_count")
    private Long operationCount;

    @JsonProperty("envelope_xdr")
    private String envelopeXdr;

    @JsonProperty("result_xdr")
    private String resultXdr;

    @JsonProperty("result_meta_xdr")
    private String resultMetaXdr;

    @JsonProperty("fee_meta_xdr")
    private String feeMetaXdr;

    @JsonProperty("memo_type")
    private String memoType;

    private List<String> signatures;

    // @TODO - parse date
    @JsonProperty("valid_after")
    private String validAfter;

    @JsonProperty("ledger_attr")
    private Long ledgerAttr;
}
