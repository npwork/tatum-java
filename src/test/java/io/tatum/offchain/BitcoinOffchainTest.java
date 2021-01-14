package io.tatum.offchain;

import io.tatum.model.request.KeyPair;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import org.junit.Test;

public class BitcoinOffchainTest {

    @Test
    public void prepareBitcoinSignedOffchainTransactionTest() {

        boolean testnet = true;
        WithdrawalResponseData[] data;
        String amount = "0.00050000";
        String address = ;
        String mnemonic = "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten";
        KeyPair[] keyPair;
        String changeAddress;

        BitcoinOffchain bitcoinOffchain = new BitcoinOffchain();
        bitcoinOffchain.prepareBitcoinSignedOffchainTransaction(true, );

    }
}
