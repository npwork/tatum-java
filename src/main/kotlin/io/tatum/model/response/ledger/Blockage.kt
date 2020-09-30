package io.tatum.model.response.ledger

/**
 * Blockage
 *
 * @property id ID of the blockage.
 * @property accountId ID of the account this blockage is for.
 * @property amount Amount blocked on account.
 * @property type Type of blockage.
 * @property description Description of blockage.
 */
class Blockage(
    val id: String,
    val accountId: String,
    val amount: String,
    val type: String,
    val description: String? = null,
)
