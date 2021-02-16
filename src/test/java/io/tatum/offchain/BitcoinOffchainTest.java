package io.tatum.offchain;

import io.tatum.model.request.Currency;
import io.tatum.model.request.KeyPair;
import io.tatum.model.response.offchain.Address;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import io.tatum.model.wallet.Wallet;
import io.tatum.wallet.WalletGenerator;
import org.bitcoinj.core.*;
import org.junit.Test;

import static io.tatum.constants.Constant.BITCOIN_TESTNET;
import static org.bitcoinj.core.Utils.HEX;

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
        String preparedTx = bitcoinOffchain.prepareBitcoinSignedOffchainTransaction(testnet, data, amount, address,
                mnemonic, keyPair, changeAddress, null);
        System.out.println(preparedTx);

    }

    @Test
    public void prepareBitcoinSignedOffchainTransactionTest_changeAddress() throws Exception {

        boolean testnet = true;
        WithdrawalResponseData[] data = new WithdrawalResponseData[2];
        WithdrawalResponseData out = new WithdrawalResponseData();
        out.setVIn("-1");
        out.setAmount("0.00148000"); // send to the change address
        data[0] = out;

        WithdrawalResponseData in = new WithdrawalResponseData();
        in.setVIn("e93e9c0bc64f34170b0c0c6a97763b05584310b8034272abfb364a9e21cdc03e");
        in.setAmount("0.00200000"); // amount from the input
        in.setVInIndex(1); // decode the previous transaction to get this value

        Address sender = new Address();
        sender.setAddress("mfbPS2yrNc1fopS9aHwPNJeQHqrpFw9wLW");
        in.setAddress(sender);
        data[1] = in;

        String amountToSend = "0.00050000";
        String receiver = "n3XsLoopG4pbQRh2PBRJMqq5tU1zCB62kF";
        String mnemonic = null;

        KeyPair[] keyPair = new KeyPair[1];
        KeyPair pair = new KeyPair();
        pair.setPrivateKey("cRGEdLcr32C4XuhMgsB1V4KnRHXb4Q8hZJHSZvSrjLkVci9c3x1a");
        pair.setAddress("mfbPS2yrNc1fopS9aHwPNJeQHqrpFw9wLW");
        keyPair[0] = pair;

        String changeAddress = "n3DS6fdDBCva3AKz12YmDSeBnNCF7Tuwvv";

        BitcoinOffchain bitcoinOffchain = new BitcoinOffchain();
        String preparedTx = bitcoinOffchain.prepareBitcoinSignedOffchainTransaction(testnet, data, amountToSend,
                receiver, mnemonic, keyPair, changeAddress, null);
        System.out.println(preparedTx);

        byte[] hashd = Sha256Hash.hashTwice(HEX.decode(preparedTx));
        byte[] reverseBytes = Utils.reverseBytes(hashd);
        String txHash = HEX.encode(reverseBytes);
        System.out.println(txHash);
        System.out.println("https://tbtc.bitaps.com/raw/transaction/" + txHash);
        System.out.println("https://tbtc.bitaps.com/" + txHash);
    }

    @Test
    public void walletTest() throws Exception {
        Wallet wallet = WalletGenerator.generateWallet(Currency.BTC, true,
                "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten");

        int i = 102;
        System.out.println("ith " + i);

        String xpub = wallet.getXpub();
        System.out.println("xpub: " + xpub);

        String privKey = io.tatum.wallet.Address.generatePrivateKeyFromMnemonic(Currency.BTC, true, "quantum tobacco key they maid mean crime youth chief jungle mind design broken tilt bus shoulder leaf good forward erupt split divert bread kitten", i);
        System.out.println("Private key from mnemonic");
        System.out.println(privKey);

        String addr = io.tatum.wallet.Address.generateAddressFromXPub(Currency.BTC, true, xpub, i);
        System.out.println("Address from xpub");
        System.out.println(addr);

        ECKey ecKey = DumpedPrivateKey.fromBase58(BITCOIN_TESTNET, privKey).getKey();
        org.bitcoinj.core.Address address1 = LegacyAddress.fromKey(BITCOIN_TESTNET, ecKey);
        System.out.println("Address from private key");
        System.out.println(address1.toString());
    }
}
