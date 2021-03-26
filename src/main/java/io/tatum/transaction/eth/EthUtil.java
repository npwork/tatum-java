package io.tatum.transaction.eth;

import io.tatum.model.request.transaction.Fee;
import io.tatum.utils.Async;
import io.tatum.utils.NumericUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static org.web3j.utils.Convert.Unit.GWEI;

/**
 * The type Eth util.
 */
@Log4j2
public class EthUtil {

    /**
     * To hex string string.
     *
     * @param data the data
     * @return the string
     */
    public static final String toHexString(String data) {
        if (StringUtils.isNotEmpty(data)) {
            if (Numeric.containsHexPrefix(data) || NumericUtil.isHexadecimal(data)) {
                return data;
            } else {
                return Numeric.toHexString(data.getBytes());
            }
        }
        return "0x0";
    }

    /**
     * Estimate gas big integer.
     *
     * @param web3j       the web 3 j
     * @param transaction the transaction
     * @return the big integer
     * @throws IOException the io exception
     */
    public static BigInteger estimateGas(Web3j web3j, Transaction transaction) throws IOException {
        EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
        if (ethEstimateGas.getError() != null) {
            log.error(ethEstimateGas.getError().getMessage());
        }
        return ethEstimateGas.getAmountUsed().add(BigInteger.valueOf(5000));
    }

    /**
     * Convert amount big integer.
     *
     * @param amount the amount
     * @param digits the digits
     * @return the big integer
     */
    public static BigInteger convertAmount(String amount, int digits) {
        return new BigDecimal(amount).toBigInteger().multiply(BigInteger.TEN.pow(digits));
    }

    /**
     * Gets gas price.
     *
     * @param fee the fee
     * @return the gas price
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public static BigInteger getGasPrice(Fee fee) throws ExecutionException, InterruptedException {
        return fee != null ? new BigInteger(fee.getGasPrice()) : ethGetGasPriceInWei().toBigInteger();
    }

    /**
     * Estimate Gas price for the transaction.
     *
     * @return the big decimal
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public static BigDecimal ethGetGasPriceInWei() throws ExecutionException, InterruptedException {
        var data = Async.getJson("https://ethgasstation.info/json/ethgasAPI.json");
        JSONObject jsonObject = new JSONObject(data);
        return Convert.toWei(jsonObject.getBigDecimal("fast").divide(BigDecimal.TEN), GWEI);
    }
}
