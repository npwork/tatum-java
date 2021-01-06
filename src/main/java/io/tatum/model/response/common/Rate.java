package io.tatum.model.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.tatum.model.response.ledger.Fiat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Rate.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rate {

    private Money id;
    private String value;
    private Fiat basePair;
    private BigDecimal timestamp;
    private String source;

}
