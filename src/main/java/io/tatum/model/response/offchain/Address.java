package io.tatum.model.response.offchain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
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
    private Optional<BigDecimal> derivationKey;

    /**
     * Extended public key to derive address from. In case of XRP, this is account address,
     * since address is defined as DestinationTag, which is address field. In case of XLM, this is account address, since address is defined as message, which is address field.
     *
     * @type {string}
     * @memberof Address
     */
    private Optional<String> xpub;

    /**
     * In case of XRP, destinationTag is the distinguisher of the account.
     *
     * @type {number}
     * @memberof Address
     */
    private Optional<BigDecimal> destinatinTag;

    /**
     * In case of XLM, message is the distinguisher of the account.
     *
     * @type {string}
     * @memberof Address
     */
    private Optional<String> message;
}