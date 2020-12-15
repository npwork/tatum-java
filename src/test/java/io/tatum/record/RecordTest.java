package io.tatum.record;

import io.tatum.model.request.Currency;
import io.tatum.model.response.common.Rate;
import io.tatum.model.response.eth.Log;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class RecordTest {
    @Test
    public void getLogRecordTest() throws ExecutionException, InterruptedException {
        Record record = new Record();
        // https://etherscan.io/tx/0xffd125fb072d2974c14576a2f777f3386535db5b85c0d120066dc8fbd5d7680a
        Log log = record.getLogRecord(Currency.ETH, "0xffd125fb072d2974c14576a2f777f3386535db5b85c0d120066dc8fbd5d7680a");
        System.out.println(log.getData());
    }
}
