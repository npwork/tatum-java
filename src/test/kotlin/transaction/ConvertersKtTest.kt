package transaction

import io.tatum.transaction.ethToWei
import io.tatum.transaction.gweiToWei
import io.tatum.transaction.weiToEth
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

val MIN_NONZERO_WEI = BigInteger.ONE // minimum non-zero
val MIN_NONZERO_GWEI = BigDecimal.valueOf(0.000000001) // minimum non-zero
val MIN_NONZERO_ETH = BigDecimal.valueOf(0.000000000000000001).stripTrailingZeros() // minimum non-zero


internal class ConvertersKtTest {

    @Test
    fun ethToWei() {
        val actualWei = MIN_NONZERO_ETH.ethToWei()

        assertThat(actualWei).isEqualTo(MIN_NONZERO_WEI)
    }

    @Test
    fun gweiToWei() {
        val actualWei = MIN_NONZERO_GWEI.gweiToWei()

        assertThat(actualWei).isEqualTo(MIN_NONZERO_WEI)
    }

    @Test
    fun weiToEth() {
        val actualEth: BigDecimal = MIN_NONZERO_WEI.weiToEth()

        assertThat(actualEth).isEqualTo(MIN_NONZERO_ETH)
        assertThat(actualEth.toPlainString()).isEqualTo(MIN_NONZERO_ETH.toPlainString())
    }
}