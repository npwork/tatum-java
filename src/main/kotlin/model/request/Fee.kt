package model.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.readValue
import khttp.get
import mapper
import transaction.Gas
import transaction.gweiToWei
import java.math.BigDecimal
import java.math.BigInteger

class Fee(
    val gasLimit: BigInteger,
    val gasPrice: BigInteger,
) {
    init {
        require(gasLimit >= BigInteger.ZERO) {"gasLimit cannot be negative number."}
        require(gasPrice >= BigInteger.ZERO) {"gasPrice cannot be negative number."}
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class GweiResponse(val fast: Long)

/**
 * Estimate Gas price for a transaction.
 */
fun getGasPriceInGwei(): BigDecimal {
    val response = get("https://ethgasstation.info/json/ethgasAPI.json")
    val data = response.jsonObject.toString()
    val gwei: GweiResponse = mapper.readValue(data)

    return BigDecimal(gwei.fast).div(BigDecimal.TEN)
}

/**
 * Gas Limit differs if data is present and increases with data length.
 */
private fun estimateGasLimit(data: ByteArray): Long = if (data.isEmpty()) {
    Gas.GTRANSACTION.value + Gas.GTXDATAZERO.value
} else {
    Gas.GTRANSACTION.value + Gas.GTXDATANONZERO.value * data.size
}

fun estimateTransactionFee(data: ByteArray) = Fee(
    estimateGasLimit(data).toBigInteger(),
    getGasPriceInGwei().gweiToWei(),
)
