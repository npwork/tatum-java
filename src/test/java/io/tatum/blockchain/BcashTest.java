package io.tatum.blockchain;

import io.restassured.RestAssured;
import io.tatum.model.response.bch.BchBlock;
import io.tatum.model.response.bch.BchInfo;
import io.tatum.model.response.bch.BchTx;
import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.common.TransactionHash;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.TATUM_API_URL;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class BcashTest {

    @Before
    public void setup() {
        RestAssured.baseURI = TATUM_API_URL;
        RestAssured.port = 443;
    }

    @Test
    public void bcashBroadcastTest() throws InterruptedException, ExecutionException, IOException {
        String txData = "62BD544D1B9031EFC330A3E855CC3A0D51CA5131455C1AB3BCAC6D243F65460D";
        String signatureId = "1f7f7c0c-3906-4aa1-9dfe-4b67c43918f6";
        Bcash bcash = new Bcash();
        TransactionHash hash = bcash.bcashBroadcast(txData, null);
    }

    @Test
    public void bcashGetCurrentBlockTest() throws InterruptedException, ExecutionException, IOException {
        BchInfo bchInfo = new Bcash().bcashGetCurrentBlock();
        System.out.println(bchInfo.toString());
        assertThat(bchInfo, hasProperty("chain"));
        assertThat(bchInfo, hasProperty("blocks"));
        assertThat(bchInfo, hasProperty("bestblockhash"));
        assertThat(bchInfo, hasProperty("difficulty"));
    }

    @Test
    public void bcashGetBlockTest() throws InterruptedException, ExecutionException, IOException {
        BchInfo bchInfo = new Bcash().bcashGetCurrentBlock();
        String hash = bchInfo.getBestblockhash();

        BchBlock bchBlock = new Bcash().bcashGetBlock(hash);
        System.out.println(bchBlock.toString());

        assertThat(bchBlock, hasProperty("hash", equalTo(hash)));
        assertThat(bchBlock, hasProperty("merkleroot"));
        assertThat(bchBlock, hasProperty("previousblockhash"));
        assertThat(bchBlock, hasProperty("nextblockhash"));

    }

    @Test
    public void bcashGetBlockHashTest() throws InterruptedException, ExecutionException, IOException {
        BlockHash blockHash = new Bcash().bcashGetBlockHash(new BigDecimal(1580));
        if (blockHash != null) {
            System.out.println(blockHash.toString());
            assertThat(blockHash, hasProperty("hash"));
        }
    }

    @Test
    public void bcashGetTxForAccountTest() throws InterruptedException, ExecutionException, IOException {
        BchTx[] bchTxes = new Bcash().bcashGetTxForAccount("bitcoincash:qrdl66g8w2y38cc39s2ksjua0qq0rch4agr0kly3v4", BigDecimal.ZERO);
        if (bchTxes != null) {
            System.out.println(bchTxes[0]);
            assertThat(bchTxes[0], hasProperty("locktime"));
        }
    }

    @Test
    public void bcashGetTransactionTest() throws InterruptedException, ExecutionException, IOException {
        BchTx bchTx = new Bcash().bcashGetTransaction("5bf6209299d4c53ffe720f048ed9ce65265745f1ec9b888929c0d0cfdb99d45c");
        if (bchTx != null) {
            System.out.println(bchTx);
            assertThat(bchTx, hasProperty("locktime"));
            assertThat(bchTx, hasProperty("vin"));
            assertThat(bchTx, hasProperty("vout"));
        }
    }
}
