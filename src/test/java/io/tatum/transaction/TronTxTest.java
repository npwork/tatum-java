package io.tatum.transaction;

import com.google.protobuf.InvalidProtocolBufferException;
import io.tatum.model.request.FreezeTron;
import io.tatum.model.request.Resource;
import io.tatum.model.request.TransferTron;
import io.tatum.model.request.TransferTronTrc20;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Test;
import org.tron.core.exception.CancelException;
import org.tron.tronj.abi.FunctionEncoder;
import org.tron.tronj.abi.TypeReference;
import org.tron.tronj.abi.datatypes.Address;
import org.tron.tronj.abi.datatypes.Bool;
import org.tron.tronj.abi.datatypes.Function;
import org.tron.tronj.abi.datatypes.generated.Uint256;
import org.tron.tronj.client.TronClient;
import org.tron.tronj.proto.Chain;
import org.tron.tronj.proto.Contract;
import org.tron.tronj.proto.Response;

import java.math.BigInteger;
import java.util.Arrays;

public class TronTxTest {

    @Test
    public void prepareTronSignedTransaction_1_Test() throws InvalidProtocolBufferException {

        TransferTron body = new TransferTron();
        body.setFromPrivateKey("842E09EB40D8175979EFB0071B28163E11AED0F14BDD84090A4CEFB936EF5701");
        body.setAmount("0.000001"); // 1 TRX
        body.setTo("TVAEYCmc15awaDRAjUZ1kvcHwQQaoPw2CW");

        TronTx tronTx = new TronTx();
        tronTx.prepareTronSignedTransaction(true, body);
    }

    @Test
    public void prepareTronFreezeTransactionTest() throws InvalidProtocolBufferException, CancelException {
        FreezeTron body = new FreezeTron();
        body.setFromPrivateKey("842E09EB40D8175979EFB0071B28163E11AED0F14BDD84090A4CEFB936EF5701");
        body.setAmount("100"); // 100_000_000 TRX
        body.setReceiver("TVAEYCmc15awaDRAjUZ1kvcHwQQaoPw2CW");
        body.setDuration(3);
        body.setResource(Resource.ENERGY);

        TronTx tronTx = new TronTx();
        tronTx.prepareTronFreezeTransaction(true, body);
    }

    @Test
    public void prepareTronTrc20SignedTransactionTest() {

        TransferTronTrc20 body = new TransferTronTrc20();
        body.setTokenAddress("TWgHeettKLgq1hCdEUPaZNCM6hPg8JkG2X");
        body.setFromPrivateKey("842E09EB40D8175979EFB0071B28163E11AED0F14BDD84090A4CEFB936EF5701");
        body.setAmount("10");
        body.setFeeLimit(100_000_000L);
        body.setTo("TVAEYCmc15awaDRAjUZ1kvcHwQQaoPw2CW");

        TronTx tronTx = new TronTx();
        String tx = tronTx.prepareTronTrc20SignedTransaction(true, body);
        System.out.println(tx);
    }
}
