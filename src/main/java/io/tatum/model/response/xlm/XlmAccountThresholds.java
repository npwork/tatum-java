package io.tatum.model.response.xlm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class XlmAccountThresholds {
    @JsonProperty("low_threshold")
    private Long lowThreshold;

    @JsonProperty("med_threshold")
    private Long medThreshold;

    @JsonProperty("high_threshold")
    private Long highThreshold;
}
