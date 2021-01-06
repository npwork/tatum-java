package io.tatum.model.response.ledger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Blockage.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blockage {

    /**
     * ID of the blockage.
     * @type {string}
     * @memberof Blockage
     */
    private String id;

    /**
     * ID of the account this blockage is for.
     * @type {string}
     * @memberof Blockage
     */
    private String accountId;

    /**
     * Amount blocked on account.
     * @type {string}
     * @memberof Blockage
     */
    private String amount;

    /**
     * Type of blockage.
     * @type {string}
     * @memberof Blockage
     */
    private String type;

    /**
     * Description of blockage.
     * @type {string}
     * @memberof Blockage
     */
    private String description;

}
