package model.response.ledger

import model.request.Country

class Customer(
    val id: String,
    val externalId: String,
    val customerCountry: Country? = null,
    val accountingCurrency: Fiat? = null,
    val providerCountry: Country?,
    val active: Boolean,
    val enabled: Boolean,
)
