package io.tatum.model.request

import io.tatum.model.response.ledger.Fiat

class CustomerUpdate(
    val customerCountry: Country? = null,
    val accountingCurrency: Fiat? = null,
    val providerCountry: Country? = null,
    val externalId: String,
) {
    init {
        require(externalId.length in 1..100) {"Length of externalId has to be in range 1..100."}
    }
}