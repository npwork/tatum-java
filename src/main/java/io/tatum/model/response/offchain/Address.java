package io.tatum.model.response.offchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The type Address.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
    /**
     * Blockchain address.
     *
     * @type {string}
     * @memberof Address
     */
    private String address;

    /**
     * Currency of generated address. BTC, LTC, BCH, ETH, XRP, ERC20.
     *
     * @type {string}
     * @memberof Address
     */
    private String currency;

    /**
     * Derivation key index for given address.
     *
     * @type {number}
     * @memberof Address
     */
    private int derivationKey;

    /**
     * Extended public key to derive address from. In case of XRP, this is account address,
     * since address is defined as DestinationTag, which is address field. In case of XLM, this is account address, since address is defined as message, which is address field.
     *
     * @type {string}
     * @memberof Address
     */
    private String xpub;

    /**
     * In case of XRP, destinationTag is the distinguisher of the account.
     *
     * @type {number}
     * @memberof Address
     */
    private BigInteger destinatinTag;

    /**
     * In case of XLM, message is the distinguisher of the account.
     *
     * @type {string}
     * @memberof Address
     */
    private String message;
}
