package io.tatum.offchain;

import io.tatum.model.request.KeyPair;
import io.tatum.model.response.offchain.Address;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import org.junit.Test;

public class BitcoinOffchainTest {

    @Test
    public void prepareBitcoinSignedOffchainTransactionTest() throws Exception {

        boolean testnet = true;
        WithdrawalResponseData[] data = new WithdrawalResponseData[2];
        WithdrawalResponseData responseData1 = new WithdrawalResponseData();
        responseData1.setVIn("-1");
        responseData1.setAmount("0.00050000");
        data[0] = responseData1;

        WithdrawalResponseData responseData2 = new WithdrawalResponseData();
        responseData2.setVIn("923d82467684e2dc2281d80e5fa4e8e81a8a9080c1e2720388a9ce1f89813f26");
        responseData2.setAmount("0.00100000");
        responseData2.setVInIndex(0);

        Address _address = new Address();
        _address.setAddress("moTqYC1CiMF99JtEwzZ5MbJuox5uz1UaJX");
        responseData2.setAddress(_address);
        data[1] = responseData2;

        String amount = "0.00050000";
        String address = "mgW4ZkNK7PZUEjfgbZC5QfQHfEJH112usb";
        String mnemonic = null;

        KeyPair[] keyPair = new KeyPair[1];
        KeyPair pair = new KeyPair();
        pair.setPrivateKey("cNyM8tE1XirekMGBMYPmMFd8oL3NJv6xYgtgeB82QGQo1KvJ2w2K");
        pair.setAddress("moTqYC1CiMF99JtEwzZ5MbJuox5uz1UaJX");
        keyPair[0] = pair;

        String changeAddress = "mgW4ZkNK7PZUEjfgbZC5QfQHfEJH112usb";

        BitcoinOffchain bitcoinOffchain = new BitcoinOffchain();
        String preparedTx = bitcoinOffchain.prepareBitcoinSignedOffchainTransaction(testnet, data, amount, address, mnemonic, keyPair, changeAddress);
        System.out.println(preparedTx);

    }

    @Test
    public void prepareBitcoinSignedOffchainTransactionTest_changeAddress() throws Exception {

        boolean testnet = true;
        WithdrawalResponseData[] data = new WithdrawalResponseData[2];
        WithdrawalResponseData responseData1 = new WithdrawalResponseData();
        responseData1.setVIn("-1");
        responseData1.setAmount("0.00050000");
        data[0] = responseData1;

        WithdrawalResponseData responseData2 = new WithdrawalResponseData();
        responseData2.setVIn("923d82467684e2dc2281d80e5fa4e8e81a8a9080c1e2720388a9ce1f89813f26");
        responseData2.setAmount("0.00100000");
        responseData2.setVInIndex(0);

        Address _address = new Address();
        _address.setAddress("moTqYC1CiMF99JtEwzZ5MbJuox5uz1UaJX");
        responseData2.setAddress(_address);
        data[1] = responseData2;

        String amount = "0.00050000";
        String address = "mgW4ZkNK7PZUEjfgbZC5QfQHfEJH112usb";
        String mnemonic = null;

        KeyPair[] keyPair = new KeyPair[1];
        KeyPair pair = new KeyPair();
        pair.setPrivateKey("cNyM8tE1XirekMGBMYPmMFd8oL3NJv6xYgtgeB82QGQo1KvJ2w2K");
        pair.setAddress("moTqYC1CiMF99JtEwzZ5MbJuox5uz1UaJX");
        keyPair[0] = pair;

        String changeAddress = "tb1qrdpw2pua5pj9y64p3yq6p64n2wl36wjvxx2fr6";

        BitcoinOffchain bitcoinOffchain = new BitcoinOffchain();
        String preparedTx = bitcoinOffchain.prepareBitcoinSignedOffchainTransaction(testnet, data, amount, address, mnemonic, keyPair, changeAddress, true);
        System.out.println(preparedTx);

    }
}
