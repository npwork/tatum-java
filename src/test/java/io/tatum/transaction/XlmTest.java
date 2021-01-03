package io.tatum.transaction;

import io.tatum.blockchain.XLM;
import io.tatum.model.request.TransferXlm;
import io.tatum.model.response.xlm.Account;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.stellar.sdk.*;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

//@PrepareForTest(XlmTx.class)
public class XlmTest {

    @Test
    public void should_test_XLM_transaction_data() throws ExecutionException, InterruptedException {

        Account sequence = new Account();
        sequence.setSequence(123L);

        XLM xlm = new XLM();
        XLM spy = Mockito.spy(xlm);

        var body = new TransferXlm();
        body.setFromSecret("SCFCTIS5326CRI3XFFBEWGXFWZK3HTUFI2AOI5IJUZAX2W5KM2PXIFIQ");
        body.setAmount("1");
        body.setTo("GB4HCKVMM6SVPVSO7SFYS7DUU2C5KESP3ZOVGOHG32MLC7T4B6G4ZBLO");

        String message = body.getMessage();
        Memo memo = StringUtils.isNotEmpty(message) ? message.length() > 20 ? Memo.hash(message) : Memo.text(message)
                : Memo.text(Strings.EMPTY);

        KeyPair keyPair = KeyPair.fromSecretSeed(body.getFromSecret());
        var fromAccount = keyPair.getAccountId();

        when(spy.xlmGetAccountInfo(fromAccount)).thenReturn(sequence);

        org.stellar.sdk.Account account = new org.stellar.sdk.Account(fromAccount, sequence.getSequence());

        Network network = true ? Network.TESTNET : Network.PUBLIC;
        var builder = new Transaction.Builder(account, network)
                .setBaseFee(100)
                .addMemo(memo)
                .setTimeout(30);

        KeyPair destination = KeyPair.fromAccountId(body.getTo());

        var tx = body.isInitialize() ?
                builder.addOperation(
                        new CreateAccountOperation.Builder(destination.getAccountId(), body.getAmount()).build()).build()
                : builder.addOperation(
                new PaymentOperation.Builder(destination.getAccountId(),
                        new AssetTypeNative(), body.getAmount()).build()).build();

        tx.sign(keyPair);

        assertTrue(tx.toEnvelopeXdrBase64().contains("AAAAAgAAAAA0EqaLPO0bvBPvzGz4wucBtxmNXs"));
    }
}
