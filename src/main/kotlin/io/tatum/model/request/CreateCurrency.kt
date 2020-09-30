package io.tatum.model.request

import io.tatum.MergedCurrency
import io.tatum.model.response.ledger.Fiat
import java.math.BigDecimal

internal val VC_REGEX = "^VC_[a-zA-Z0-9_]+\$".toRegex()

class CreateCurrency(
val name: String,
supply: BigDecimal,
val description: String? = null,
val accountCode: String? = null,
val basePair: MergedCurrency,
val baseRate: BigDecimal? = null,
val accountingCurrency: Fiat? = null,

val customer: CustomerUpdate? = null,
) {
    init {
        require(name.length in 1..30 && name.matches(VC_REGEX))
        require(supply.toString().length <= 38)
        if (description != null) require(description.length in 1..100)
        if (accountCode != null) require(accountCode.length in 1..50)
        if (baseRate != null) require(baseRate >= BigDecimal.ZERO)
    }
    val supply: String = supply.toString()
}
