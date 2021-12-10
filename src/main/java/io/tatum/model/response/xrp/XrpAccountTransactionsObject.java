package io.tatum.model.response.xrp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class XrpAccountTransactionsObject {
    @NotNull
    private XrpTransaction tx;

    private Boolean validated;

    private XrpTransactionMetadata meta;
}
