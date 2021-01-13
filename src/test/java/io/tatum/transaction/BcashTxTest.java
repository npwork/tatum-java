package io.tatum.transaction;

import com.google.common.collect.ImmutableList;
import io.tatum.model.request.transaction.FromUTXOBcash;
import io.tatum.model.request.transaction.To;
import io.tatum.model.request.transaction.TransferBchBlockchain;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.transaction.bcash.TransactionBuilder;
import org.bitcoincashj.core.*;
import org.bitcoincashj.crypto.TransactionSignature;
import org.bitcoincashj.params.TestNet3Params;
import org.bitcoincashj.script.Script;
import org.bitcoincashj.script.ScriptBuilder;
import org.bitcoincashj.script.ScriptChunk;
import org.hamcrest.core.IsNot;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.BCH_MAINNET;
import static io.tatum.constants.Constant.BCH_TESTNET;
import static org.bitcoincashj.core.Utils.HEX;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class BcashTxTest {

    @Test
    public void validateLegacyTestnet() {
        String legacyP2PKHValid = "mv4rnyY3Su5gjcDNzbMLKBQkBicCtHUtFB";
        String legacyP2PKHInvalid = "mv4rnyY3vu5gjcDNzbMLKBQkBicCtHUtFB";

        assertTrue(Address.isValidLegacyAddress(TestNet3Params.get(), legacyP2PKHValid));
        assertFalse(Address.isValidLegacyAddress(TestNet3Params.get(), legacyP2PKHInvalid));

        String legacyToCashAddrValid = CashAddressFactory.create().getFromBase58(TestNet3Params.get(), legacyP2PKHValid).toString();
        assertTrue(Address.isValidCashAddr(TestNet3Params.get(), legacyToCashAddrValid));
        assertEquals("bchtest:qz0e574avqxqe2srnqa80jxrm78qvt9jlgkt79xh6k", legacyToCashAddrValid);
    }

    @Test
    public void generateAddress() {

        ECKey ecKey = ECKey.fromPrivate(new BigInteger("11041978009"));
        Address address = Address.fromKey(BCH_MAINNET, ecKey);
        System.out.println(address.toBase58());
        System.out.println(ecKey.getPublicKeyAsHex());
        System.out.println(ecKey.getPrivateKeyAsHex());

        String legacyToCashAddrValid = CashAddressFactory.create().getFromBase58(BCH_MAINNET, address.toBase58()).toString();
        System.out.println(legacyToCashAddrValid);
    }

    @Test
    public void generateAddressTestnet() {

        ECKey ecKey = ECKey.fromPrivate(new BigInteger("11041978099"));
        Address address = Address.fromKey(BCH_TESTNET, ecKey);
        System.out.println(address.toBase58());
        System.out.println(ecKey.getPrivateKeyAsWiF(BCH_TESTNET));
        System.out.println(ecKey.getPublicKeyAsHex());
        System.out.println(ecKey.getPrivateKeyAsHex());

        String legacyToCashAddrValid = CashAddressFactory.create().getFromBase58(BCH_TESTNET, address.toBase58()).toString();
        System.out.println(legacyToCashAddrValid);
    }
}
