package io.tatum.transaction

import org.kethereum.ETH_IN_WEI
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Amount of 1 Gwei in Wei.
 */
const val GWEI_IN_WEI = 1000000000

/**
 * Extension function to convert decimal currency coin to Wei.
 * ETH_IN_WEI constant should be enough to cover all decimal places of BigDecimal.
 */
fun BigDecimal.ethToWei(): BigInteger = (this * BigDecimal(ETH_IN_WEI)).toBigInteger()

/**
 * Extension function to convert decimal Gwei to Wei.
 */
fun BigDecimal.gweiToWei(): BigInteger = (this * BigDecimal(GWEI_IN_WEI)).toBigInteger()

/**
 * Extension function to convert Wei (BigInteger) to Ethereum (BigDecimal).
 */
fun BigInteger.weiToEth(): BigDecimal = (this.toBigDecimal().divide(ETH_IN_WEI.toBigDecimal()))
