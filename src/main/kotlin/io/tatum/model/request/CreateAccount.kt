package io.tatum.model.request

import io.tatum.model.response.ledger.Fiat

/**
 * @property currency Account currency. Supported values are BTC, LTC, BCH, ETH, XLM, XRP, Tatum Virtual Currencies started with VC_ prefix (this includes FIAT currencies), USDT, LEO, LINK, FREE, MKR, USDC, BAT, TUSD, PAX, PAXG, PLTC, MMY, XCON, ERC20 custom tokens registered in the Tatum Platform, XLM or XRP Assets created via Tatum Platform. ERC20 tokens USDT, LEO, LINK, FREE, MKR, USDC, BAT, TUSD, PAX, PLTC, XCON, MMY do not have Testnet blockchains, so it is impossible to use them in a non-production environment.
 * @property xpub Extended public key to generate addresses from.
 * @property compliant Enable compliant checks. If this is enabled, it is impossible to create account if compliant check fails.
 * @property accountingCurrency All transaction will be accounted in this currency for all accounts. io.tatum.Currency can be overridden per account level. If not set, customer accountingCurrency is used or EUR by default. ISO-4217
 * @property accountCode For bookkeeping to distinct account purpose.
 * @property customer If customer is filled then is created or updated.
 */
class CreateAccount(
    val currency: String,
    val xpub: String? = null,
    val compliant: Boolean = false,
    val accountingCurrency: Fiat? = null,
    val accountCode: String? = null,
    val accountNumber: String? = null,
    val customer: CustomerUpdate? = null,
) {
    init {
        require(currency.length in 2..40) {"Length of currency has to be in range 2..40."}
        if (xpub != null) require(xpub.length <= 150) {"Length of xpub has to be less then 150."}
        if (accountCode != null) require(accountCode.length in 1..50)
            {"Length of accountCode has to be in range 1..50."}
        if (accountNumber != null) require(accountNumber.length in 1..30)
            {"Length of accountNumber has to be in range 1..30."}
    }
}
