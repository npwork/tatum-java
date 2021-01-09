package io.tatum.transaction;

import com.google.common.collect.ImmutableList;
import io.tatum.model.request.transaction.FromUTXOBcash;
import io.tatum.model.request.transaction.To;
import io.tatum.model.request.transaction.TransferBchBlockchain;
import io.tatum.transaction.bcash.TransactionBuilder;
import org.bitcoincashj.core.*;
import org.bitcoincashj.crypto.TransactionSignature;
import org.bitcoincashj.params.TestNet3Params;
import org.bitcoincashj.script.Script;
import org.bitcoincashj.script.ScriptBuilder;
import org.bitcoincashj.script.ScriptChunk;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Test;

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
    public void prepareBitcoinCashSignedTransactionTest() throws ExecutionException, InterruptedException {

        var body = new TransferBchBlockchain();
        FromUTXOBcash[] fromUTXO = new FromUTXOBcash[1];
        fromUTXO[0] = new FromUTXOBcash();
        fromUTXO[0].setTxHash("53faa103e8217e1520f5149a4e8c84aeb58e55bdab11164a95e69a8ca50f8fcc");
        fromUTXO[0].setIndex(0);
        fromUTXO[0].setValue(new BigDecimal("0.0001"));
        fromUTXO[0].setPrivateKey("cVX7YtgL5muLTPncHFhP95oitV1mqUUA5VeSn8HeCRJbPqipzobf");
        body.setFromUTXO(fromUTXO);

        To[] to = new To[1];
        to[0] = new To("mjJotvHmzEuyXZJGJXXknS6N3PWQnw6jf5", new BigDecimal(String.valueOf(0.02969944)));
        body.setTo(to);

        BcashTx tx = new BcashTx();

        /*var txData = tx.prepareBitcoinCashSignedTransaction(true, body);
        assertEquals("02000000" +
                "01" +
                // revered txid / prev_hash
                "cc8f0fa58c9ae6954a1611abbd558eb5ae848c4e9a14f520157e21e803a1fa53" +
                "0000000064" +
                // scriptSig
                "4149b200cc9f41c0247c0fe1bfa9668adbcd2edd5c04bda9446db16c34e4b6e6ed2f2c5d9db454343ce84797114779610642b03ac09cdf9754795c605ce24f4974412103b17a162956975765aa6951f6349f9ab5bf510584c5df9f6065924bfd94a08513" +
                // sequence
                "ffffffff" +
                "0158512d000000000019" +
                // script of outputs / scriptPubKey / "asm": "OP_DUP OP_HASH160 299480256432f2372df6d66e21ed48b097797c9a OP_EQUALVERIFY OP_CHECKSIG",
                "76a914299480256432f2372df6d66e21ed48b097797c9a88ac" +
                "00000000", txData);*/
    }

    @Test
    public void testP2SHOutputScript() {
        String P2SHAddressString = "mv4rnyY3Su5gjcDNzbMLKBQkBicCtHUtFB";
        Address P2SHAddress = Address.fromBase58(BCH_TESTNET, P2SHAddressString);
        Script script = ScriptBuilder.createOutputScript(P2SHAddress);
        Transaction tx = new Transaction(BCH_TESTNET);
        tx.addOutput(Coin.COIN, script);
        assertEquals(P2SHAddressString, tx.getOutput(0).getScriptPubKey().getToAddress(BCH_TESTNET).toString());
    }

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

        ECKey ecKey = ECKey.fromPrivate(new BigInteger("11041978004"));
        Address address = Address.fromKey(BCH_MAINNET, ecKey);
        System.out.println(address.toBase58());
        System.out.println(ecKey.getPublicKeyAsHex());
        System.out.println(ecKey.getPrivateKeyAsHex());

        String legacyToCashAddrValid = CashAddressFactory.create().getFromBase58(BCH_MAINNET, address.toBase58()).toString();
        System.out.println(legacyToCashAddrValid);
    }
}
