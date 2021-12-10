package io.tatum.security;


import io.tatum.blockchain.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressTest {
    private final static Address ADDRESS = new Address();

    @ParameterizedTest
    @ValueSource(strings = {
            BcashTest.ADDRESS,
            BitcoinTest.ADDRESS,
            EthereumTest.ADDRESS,
            LitecoinTest.ADDRESS,
            TronTest.ADDRESS,
            VETTest.ADDRESS,
            XLMTest.ADDRESS,
            XRPTest.ADDRESS,
    })
    public void valid(String address) throws ExecutionException, InterruptedException {
        Boolean valid = ADDRESS.checkMaliciousAddress(address);
        assertTrue(valid);
    }

    // @TODO - think about non valid addresses for testnet
}
