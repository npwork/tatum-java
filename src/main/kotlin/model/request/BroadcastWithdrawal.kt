package model.request

/**
 * @property currency Currency of signed transaction to be broadcast, BTC, LTC, BCH, ETH, XRP, ERC20
 * @property txData Raw signed transaction to be published to network.
 * @property withdrawalId Withdrawal ID to be completed by transaction broadcast
 * @property signatureId Signature ID to be completed by transaction broadcast
 */
class BroadcastWithdrawal(
    val currency: String,
    val txData: String,
    val withdrawalId: String? = null,
    val signatureId: String? = null,
)
