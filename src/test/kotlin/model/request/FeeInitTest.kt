package model.request

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.math.BigInteger

const val VALID_ABSOLUTE_1 = 256789
const val VALID_ABSOLUTE_2 = 11122

const val INVALID_IS_NEGATIVE = -256789

/**
 * We have to be sure the class instance is created only with
 * valid input arguments.
 */
internal class FeeTest {

    @ParameterizedTest
    @CsvSource(
        "$VALID_ABSOLUTE_1, $VALID_ABSOLUTE_1",
        "$VALID_ABSOLUTE_2, $VALID_ABSOLUTE_2",
        "$VALID_ABSOLUTE_1, $VALID_ABSOLUTE_2",
        "$VALID_ABSOLUTE_2, $VALID_ABSOLUTE_1",
    )
    fun `create instance`(gasLimit: String, gasPrice: String) {
        val actualFee = Fee(gasLimit.toBigInteger(), gasPrice.toBigInteger())
        Assertions.assertThat(actualFee).isInstanceOf(Fee::class.java)
    }

    @Test
    fun `fail creating instance with negative gasLimit`() {
        val actualThrow: Throwable = Assertions.catchThrowable {
            Fee(INVALID_IS_NEGATIVE.toBigInteger(), BigInteger.valueOf(11122))
        }
        Assertions.assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `fail creating instance with negative gasPrice`() {
        val actualThrow: Throwable = Assertions.catchThrowable {
            Fee(BigInteger.valueOf(11122), INVALID_IS_NEGATIVE.toBigInteger())
        }
        Assertions.assertThat(actualThrow).isInstanceOf(IllegalArgumentException::class.java)
    }
}
