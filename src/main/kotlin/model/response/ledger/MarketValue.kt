package model.response.ledger

/**
 * @property amount Value of transaction in given base pair.
 * @property currency Base pair.
 * @property sourceDate Date of validity of rate in UTC.
 * @property source Source of base pair.
 */
class MarketValue(
    val amount: String? = null,
    val currency: Fiat? = null,
    val sourceDate: Long? = null,
    val source: String? = null,
)
