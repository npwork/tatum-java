package io.tatum.model.response.ledger

/**
 * @property accountBalance Account balance represents all assets on the account, available and blocked.
 * @property availableBalance Available balance on the account represents account balance minus blocked amount on the account.
 */
class AccountBalance(
    val accountBalance: String,
    val availableBalance: String,
)
