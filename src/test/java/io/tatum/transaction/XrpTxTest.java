package io.tatum.transaction;

import io.tatum.model.request.TransferXrp;
import io.xpring.xrpl.XrpException;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(XrpTx.class )
public class XrpTxTest {

    @Test // TO-DO
    public void prepareXrpSignedTransactionTest() throws InterruptedException, ExecutionException, XrpException {

        TransferXrp body = new TransferXrp();
        body.setFromSecret("shunwft7BwrFHdcXmAA87CazLsRMY");
        body.setFromAccount("rKHuaCVSzJCFh43ji9EvFAysmu1KHdMb8N");
        body.setFee(BigDecimal.valueOf(0.00001));
        body.setAmount(BigDecimal.valueOf(1));
        body.setTo("rKHuaCVSzJCFh43ji9EvFAysmu1KHdMb8N");
        body.setSourceTag(0);
        body.setDestinationTag(0);
//        Account accountMock = new Account( BigDecimal.valueOf(1), 123);
//        XRP spy = PowerMockito.spy(new XRP());
//        Mockito.when(spy.xrpGetAccountInfo("rKHuaCVSzJCFh43ji9EvFAysmu1KHdMb8N")).thenReturn(accountMock);

        var txData = new XrpTx().prepareXrpSignedTransaction(body);
        assertEquals("12000022800000002300000000240000007B2E00000000201B000000066140000000000F424068400000000000000A732102A6736884D857E721F19B91226FBA68D638009FA44B14CD46C63CC30253C8715C74473045022100FC6E6C978E6C0E5093CE4D3A72A8A21A792EBEDB24330C05350D26EC8A10467C02203359F4F6C80F5E0A36EC50AA860526A53968E0B1C6D1CA8A3947E5D53BB44D278114C8A4688E754167637D0E2C00F14C7E15AAFDA42C8314C8A4688E754167637D0E2C00F14C7E15AAFDA42C", txData);
    }
}
