package io.tatum.model.response.ledger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class Account {

    /**
     * For bookkeeping to distinct account purpose.
     * @type {string}
     * @memberof Account
     */
    private String accountCode;

    /**
     * Account ID.
     * @type {string}
     * @memberof Account
     */
    private String id;

    /**
     *
     * @type {AccountBalance}
     * @memberof Account
     */
    private AccountBalance balance;

    /**
     * Time of account creation.
     * @type {string}
     * @memberof Account
     */
    private String created;

    /**
     * Account currency. Supported values are BTC, LTC, BCH, ETH, XRP, Tatum Virtual Currencies started with VC_ prefix or ERC20 customer token created via Tatum Platform.
     * @type {string}
     * @memberof Account
     */
    private String currency;

    /**
     * ID of newly created customer or existing customer associated with account.
     * @type {string}
     * @memberof Account
     */
    private String customerId;

    /**
     * Indicates whether account is frozen or not.
     * @type {boolean}
     * @memberof Account
     */
    private Boolean frozen;

    /**
     * Indicates whether account is active or not.
     * @type {boolean}
     * @memberof Account
     */
    private Boolean active;

    /**
     * Extended public key to derive address from.
     * In case of XRP, this is account address, since address is defined as DestinationTag, which is address field.
     * In case of XLM, this is account address, since address is defined as message, which is address field.
     * @type {string}
     * @memberof Account
     */
    private String xpub;

}
