package io.tatum.model.response.offchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * The type Withdrawal response data.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class WithdrawalResponseData {
    /**
     * @type {Address}
     * @memberof WithdrawalResponseData
     */
    private Address address;

    /**
     * Amount of unprocessed transaction outputs, that can be used for withdrawal. Bitcoin, Litecoin, Bitcoin Cash only.
     *
     * @type {number}
     * @memberof WithdrawalResponseData
     */
    @Pattern(regexp = "^[+]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")
    private String amount;

    /**
     * Last used unprocessed transaction output, that can be used.
     * Bitcoin, Litecoin, Bitcoin Cash only. If -1, it indicates prepared vOut with amount to be transferred to pool address.
     *
     * @type {string}
     * @memberof WithdrawalResponseData
     */
    private String vIn;

    /**
     * Index of last used unprocessed transaction output in raw transaction, that can be used. Bitcoin, Litecoin, Bitcoin Cash only.
     *
     * @type {number}
     * @memberof WithdrawalResponseData
     */
    private int vInIndex;

    /**
     * Script of last unprocessed UTXO. Bitcoin SV only.
     *
     * @type {string}
     * @memberof WithdrawalResponseData
     */
    private String scriptPubKey;
}
