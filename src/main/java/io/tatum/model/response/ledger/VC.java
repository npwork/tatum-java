package io.tatum.model.response.ledger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class VC {
    /**
     * Exchange rate of the base pair. Each unit of the created curency will represent value of baseRate*1 basePair.
     * @type {number}
     * @memberof VC
     */
    private BigDecimal baseRate;

    /**
     * Base pair for virtual currency. Transaction value will be calculated according to this base pair. e.g. 1 VC_VIRTUAL is equal to 1 BTC, if basePair is set to BTC.
     * @type {string}
     * @memberof VC
     */
    private BasePairEnum basePair;

    /**
     * ID of customer associated with virtual currency.
     * @type {string}
     * @memberof VC
     */
    private String customerId;

    /**
     * Used as a description within Tatum system.
     * @type {string}
     * @memberof VC
     */
    private String description;

    /**
     * Virtual currency name. Must be prefixed with 'VC_'.
     * @type {string}
     * @memberof VC
     */
    private String name;

    /**
     * Supply of virtual currency.
     * @type {string}
     * @memberof VC
     */
    private String supply;

    /**
     * Virtual currency account.
     * @type {string}
     * @memberof VC
     */
    private String accountId;

    /**
     * Address of ERC20 token.
     * @type {string}
     * @memberof VC
     */
    private String erc20Address;

    /**
     * Virtual currency account, on which initial supply was minted.
     * @type {string}
     * @memberof VC
     */
    private  String issuerAccount;

    /**
     * Blockchain, on which this virtual currency is paired on. Not present, when Tatum's private ledger is used as a base ledger.
     * @type {string}
     * @memberof VC
     */
    private String chain;

    /**
     * Ethereum address, where initial supply was minted.
     * @type {string}
     * @memberof VC
     */
    private String initialAddress;
}
