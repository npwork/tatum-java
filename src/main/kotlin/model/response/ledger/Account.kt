package model.response.ledger

/**
 * @property accountCode For bookkeeping to distinct account purpose.
 * @property id Account ID.
 * @property balance Account balance represents all assets on the account, available and blocked.
 * @property created Time of account creation.
 * @property currency Account currency. Supported values are BTC, LTC, BCH, ETH, XRP, Tatum Virtual Currencies started with VC_ prefix or ERC20 customer token created via Tatum Platform.
 * @property accountingCurrency Accounting currency.
 * @property customerId ID of newly created customer or existing customer associated with account.
 * @property frozen Indicates whether account is frozen or not.
 * @property active Indicates whether account is active or not.
 * @property xpub Extended public key to derive address from. In case of XRP, this is account address, since address is defined as DestinationTag, which is address field. In case of XLM, this is account address, since address is defined as message, which is address field.
 */
class Account(
    val accountCode: String? = null,
    val accountNumber: String? = null,
    val id: String,
    val balance: AccountBalance,
    val created: String? = null,
    val currency: String,
    val accountingCurrency: Fiat? = null,
    val customerId: String? = null,
    val frozen: Boolean,
    val active: Boolean,
    val xpub: String? = null,
)
