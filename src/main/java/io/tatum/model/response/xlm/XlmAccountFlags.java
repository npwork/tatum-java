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
public class XlmAccountFlags {
    @JsonProperty("auth_required")
    private Boolean authRequired;

    @JsonProperty("auth_revocable")
    private Boolean authRevocable;

    @JsonProperty("auth_immutable")
    private Boolean authImmutable;

    @JsonProperty("auth_clawback_enabled")
    private Boolean authClawbackEnabled;
}
