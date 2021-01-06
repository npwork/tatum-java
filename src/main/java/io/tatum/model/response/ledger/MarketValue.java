package io.tatum.model.response.ledger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * The type Market value.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketValue {
    /**
     * Value of transaction in given base pair.
     *
     * @type {string}
     * @memberof MarketValue
     */
    private String amount;

    /**
     * Base pair.
     *
     * @type {string}
     * @memberof MarketValue
     */
    private Fiat currency;

    /**
     * Date of validity of rate in UTC.
     *
     * @type {number}
     * @memberof MarketValue
     */
    private BigDecimal sourceDate;

    /**
     * Source of base pair.
     *
     * @type {string}
     * @memberof MarketValue
     */
    private String source;

}
