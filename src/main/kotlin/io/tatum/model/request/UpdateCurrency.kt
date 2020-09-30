package io.tatum.model.request

import io.tatum.MergedCurrency
import java.math.BigDecimal

class UpdateCurrency(
    val name: String,
    val basePair: MergedCurrency?,
    val baseRate: BigDecimal?,
) {
    init {
        require(name.length in 1..30 && name.matches(VC_REGEX))
        if (baseRate != null) require(baseRate >= BigDecimal.ZERO)
    }
}