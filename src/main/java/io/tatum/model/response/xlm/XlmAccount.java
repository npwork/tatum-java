package io.tatum.model.response.xlm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * The type Account.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class XlmAccount {
    private String id;

    @JsonProperty("account_id")
    private String accountId;

    private Long sequence;

    @JsonProperty("subentry_count")
    private Long subentryCount;

    @JsonProperty("last_modified_ledger")
    private Long lastModifiedLedger;

    // @TODO - parse date
    @JsonProperty("last_modified_time")
    private String lastModifiedTime;

    private XlmAccountThresholds thresholds;

    private XlmAccountFlags flags;

    private List<XlmBalance> balances;

    private List<XlmSigner> signers;

    @JsonProperty("num_sponsoring")
    private Long numSponsoring;

    @JsonProperty("num_sponsored")
    private Long numSponsored;

    @JsonProperty("paging_token")
    private String pagingToken;

    // @TODO - data_attr
}
