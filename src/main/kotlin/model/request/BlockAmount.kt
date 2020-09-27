package model.request

import java.math.BigDecimal

/**
 * Preparation class for Blockage.
 *
 * @param amount Amount to be blocked on account.
 * @property type Your type, e.g. DEBIT_CARD_OP.
 * @property type Your description, specification, comment.
 */
class BlockAmount(
    amount: BigDecimal,
    val type: String,
    val description: String? = null,
) {
    init {
        require(amount > BigDecimal.ZERO && amount.toString().length <= 38)
            {"amount has to be positive and max 38 digits."}
        require(type.length in 1..100) {"Length of type has to be in range 1..100."}
        if (description != null) require(description.length in 1..300)
            {"If description is present, it's length has to be in range 1..300."}
    }

    /**
     * Amount to be blocked on account.
     */
    val amount: String = amount.toString()
}
