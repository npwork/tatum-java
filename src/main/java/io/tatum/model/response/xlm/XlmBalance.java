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
public class XlmBalance {
    private BigDecimal balance;

    @JsonProperty("buying_liabilities")
    private BigDecimal buyingLiabilities;

    @JsonProperty("selling_liabilities")
    private BigDecimal sellingLiabilities;

    @JsonProperty("asset_type")
    private String assetType;
}
