package model.response.offchain

import java.math.BigInteger

/**
 * @property address Blockchain address.
 * @property currency Currency of generated address. BTC, LTC, BCH, ETH, XRP, ERC20.
 * @property derivationKey Derivation key index for given address.
 * @property xpub Extended public key to derive address from. In case of XRP, this is account address,
 * @property destinatinTag In case of XRP, destinationTag is the distinguisher of the account.
 * @property message In case of XLM, message is the distinguisher of the account.
 */
class Address(
    val address: String,
    val currency: String,
    val derivationKey: BigInteger? = null,
    val xpub: String? = null,
    val destinatinTag: BigInteger? = null,
    val message: String? = null,
)
