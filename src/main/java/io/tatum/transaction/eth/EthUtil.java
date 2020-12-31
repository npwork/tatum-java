package io.tatum.transaction.eth;

import io.tatum.utils.NumericUtil;
import org.apache.commons.lang3.StringUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;

public class EthUtil {

    public static final String toHexString(String data) {
        if (StringUtils.isNotEmpty(data)) {
            if (Numeric.containsHexPrefix(data)) {
                return data;
            } else if (NumericUtil.isHexadecimal(data)) {
                return Numeric.prependHexPrefix(data);
            } else {
                return Numeric.toHexString(data.getBytes());
            }
        }
        return null;
    }

    public static BigInteger estimateGas(Web3j web3j, Transaction transaction) throws IOException {
        EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
        return ethEstimateGas.getAmountUsed().add(BigInteger.valueOf(5000));
    }
}
