package io.tatum.blockchain;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.tatum.model.response.btc.BtcBlock;
import io.tatum.model.response.btc.BtcInfo;
import io.tatum.model.response.btc.BtcTx;
import io.tatum.model.response.btc.BtcUTXO;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.TransactionHash;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class BitcoinTest {

    @Test
    public void btcBroadcastTest() throws InterruptedException, ExecutionException, IOException, UnirestException {
//        String txData = "e93e9c0bc64f34170b0c0c6a97763b05584310b8034272abfb364a9e21cdc03e";
//        TransactionHash hash = new Bitcoin().btcBroadcast(txData, null);
//        System.out.println(hash);

        HttpResponse<String> response = (HttpResponse<String>) Unirest.post("https://api-eu1.tatum.io/v3/bitcoin/broadcast")
                .header("content-type", "application/json")
                .header("x-api-key", "2d443c87-212f-460f-a330-25be2c654750")
                .body("{\"txData\":\"62BD544D1B9031EFC330A3E855CC3A0D51CA5131455C1AB3BCAC6D243F65460D\"}")
                .asString();
    }

    @Test
    public void btcGetCurrentBlockTest() throws InterruptedException, ExecutionException {
        BtcInfo btcInfo = new Bitcoin().btcGetCurrentBlock();
        System.out.println(btcInfo);
        assertThat(btcInfo, hasProperty("chain"));
        assertThat(btcInfo, hasProperty("blocks"));
        assertThat(btcInfo, hasProperty("bestblockhash"));
        assertThat(btcInfo, hasProperty("difficulty"));
    }

    @Test
    public void btcGetBlockTest() throws InterruptedException, ExecutionException {
        // https://explorer.bitcoin.com/btc/block/0000000000000000000e775008c6749deb78f666b88fb285b5951ecb7894367f
        BtcBlock btcBlock = new Bitcoin().btcGetBlock("000000000000000f965504aec02813eef6cf7056a1e919d24e2d2675385865fa");
    }

    @Test
    public void btcGetBlockHashTest() throws InterruptedException, ExecutionException {
        BlockHash blockHash = new Bitcoin().btcGetBlockHash(1580);
        if (blockHash != null) {
            System.out.println(blockHash.toString());
            assertThat(blockHash, hasProperty("hash"));
        }
    }

    @Test
    public void btcGetUTXOTest() throws InterruptedException, ExecutionException {
        String hash = "0000000000000000000e775008c6749deb78f666b88fb285b5951ecb7894367f";
        Bitcoin bitcoin = new Bitcoin();
        BtcUTXO btcUTXO = bitcoin.btcGetUTXO(hash, 5);
    }

    @Test
    public void btcGetTxForAccountTest() throws InterruptedException, ExecutionException {
        Bitcoin bitcoin = new Bitcoin();
        // https://www.blockchain.com/btc/address/bc1qxy2kgdygjrsqtzq2n0yrf2493p83kkfjhx0wlh
        BtcTx[] btcTxes = bitcoin.btcGetTxForAccount("mfbPS2yrNc1fopS9aHwPNJeQHqrpFw9wLW", 4, 0);
        System.out.println(btcTxes[0]);
        System.out.println(btcTxes[0].getHeight());
        assertThat(btcTxes[0], hasProperty("hash"));
        assertThat(btcTxes[0], hasProperty("fee"));
        assertThat(btcTxes[0], hasProperty("inputs"));
        assertThat(btcTxes[0], hasProperty("rate"));

        assertThat(btcTxes[0].getInputs()[0], hasProperty("prevout"));
        assertThat(btcTxes[0].getInputs()[0], hasProperty("witness"));
    }

    @Test
    public void btcGetTransactionTest() throws InterruptedException, ExecutionException {
        // Unconfirmed
        // https://explorer.bitcoin.com/btc/tx/179c7117bc0f31cd9b42a835f0ca5eb6c6c70571c9add15c18c5da97bcc4d080
        String hash = "ca9790a9e055dcbf46dd7c52eadec766663ab087cad23432d209679fb224b30a";
        Bitcoin bitcoin = new Bitcoin();
        BtcTx btcTx = bitcoin.btcGetTransaction(hash);
        System.out.println(btcTx);
        if (btcTx != null) {
            assertThat(btcTx, hasProperty("witnessHash"));
            assertThat(btcTx, hasProperty("height"));
            assertThat(btcTx, hasProperty("block"));
            assertThat(btcTx, hasProperty("locktime"));
        }
    }

}
