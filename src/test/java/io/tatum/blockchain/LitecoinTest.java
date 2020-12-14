package io.tatum.blockchain;

import io.tatum.model.response.common.BlockHash;
import io.tatum.model.response.ltc.LtcBlock;
import io.tatum.model.response.ltc.LtcInfo;
import io.tatum.model.response.ltc.LtcTx;
import io.tatum.model.response.ltc.LtcUTXO;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

public class LitecoinTest {

    @Test
    public void ltcGetCurrentBlockTest() throws InterruptedException, ExecutionException, IOException {
        Litecoin litecoin = new Litecoin();
        LtcInfo ltcInfo = litecoin.ltcGetCurrentBlock();
        System.out.println(ltcInfo);
        assertThat(ltcInfo, hasProperty("blocks"));
        assertThat(ltcInfo, hasProperty("bestblockhash"));
        assertThat(ltcInfo, hasProperty("headers"));
        assertThat(ltcInfo, hasProperty("difficulty"));
    }

    @Test
    public void ltcGetBlockTest() throws InterruptedException, ExecutionException, IOException {
        Litecoin litecoin = new Litecoin();
        // https://live.blockcypher.com/ltc/block/3a3150fbc4c520719af42b6311c19430c054e787989e3ce32ab8c873720424fe/
        String hash = "3a3150fbc4c520719af42b6311c19430c054e787989e3ce32ab8c873720424fe";
        LtcBlock ltcBlock = litecoin.ltcGetBlock(hash);
        System.out.println(ltcBlock);
        assertThat(ltcBlock, hasProperty("prevBlock"));
        assertThat(ltcBlock, hasProperty("height"));
        assertThat(ltcBlock, hasProperty("version"));
        assertThat(ltcBlock, hasProperty("hash", equalTo(hash)));
    }

    @Test
    public void ltcGetBlockHashTest() throws InterruptedException, ExecutionException, IOException {
        Litecoin litecoin = new Litecoin();
        // https://live.blockcypher.com/ltc/block/3a3150fbc4c520719af42b6311c19430c054e787989e3ce32ab8c873720424fe/
        BigDecimal i = new BigDecimal(1964370);
        BlockHash blockHash = litecoin.ltcGetBlockHash(i);
        System.out.println(blockHash);
        assertThat(blockHash, hasProperty("hash", equalTo("3a3150fbc4c520719af42b6311c19430c054e787989e3ce32ab8c873720424fe")));
    }

    @Test
    public void ltcGetUTXOTest() throws InterruptedException, ExecutionException, IOException {

        Litecoin litecoin = new Litecoin();
        // https://live.blockcypher.com/ltc/tx/5d417e7f8d22a591cc8454a59f0c208225a0c6966bb891803f3b2c2761660581/
        String tx = "5d417e7f8d22a591cc8454a59f0c208225a0c6966bb891803f3b2c2761660581";
        BigDecimal i = new BigDecimal(0);
        LtcUTXO ltcGetUTXO = litecoin.ltcGetUTXO(tx, i);
        System.out.println(ltcGetUTXO);
        assertThat(ltcGetUTXO, hasProperty("script"));
        assertThat(ltcGetUTXO, hasProperty("address"));
        assertThat(ltcGetUTXO, hasProperty("height"));
        assertThat(ltcGetUTXO, hasProperty("coinbase"));
    }

    @Test
    public void ltcGetTxForAccountTest() throws ExecutionException, InterruptedException {
        Litecoin litecoin = new Litecoin();
        // https://live.blockcypher.com/ltc/address/LVvMRh6jv6WpQd4zFA7jf36B1oNggTiGLc/
        String address = "LVvMRh6jv6WpQd4zFA7jf36B1oNggTiGLc";
        LtcTx[] ltcTxes = litecoin.ltcGetTxForAccount(address, null, null);
        if (ltcTxes != null && ltcTxes.length > 0) {
            System.out.println(ltcTxes.length);
            System.out.println(ltcTxes[ltcTxes.length - 1]);
            LtcTx ltcTx = ltcTxes[ltcTxes.length - 1];
            assertThat(ltcTx, hasProperty("witnessHash"));
            assertThat(ltcTx, hasProperty("fee"));
            assertThat(ltcTx, hasProperty("outputs"));
            assertThat(ltcTx, hasProperty("ps"));
        }
    }

    @Test
    public void ltcGetTransactionTest() throws InterruptedException, ExecutionException, IOException {
        Litecoin litecoin = new Litecoin();
        String txHash = "5d417e7f8d22a591cc8454a59f0c208225a0c6966bb891803f3b2c2761660581";
        LtcTx ltcTx = litecoin.ltcGetTransaction(txHash);
        System.out.println(ltcTx);
        assertThat(ltcTx, hasProperty("witnessHash"));
        assertThat(ltcTx, hasProperty("fee"));
        assertThat(ltcTx, hasProperty("outputs"));
        assertThat(ltcTx, hasProperty("ps"));
    }
}
