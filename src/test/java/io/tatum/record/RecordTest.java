package io.tatum.record;

import io.tatum.blockchain.EthereumTest;
import io.tatum.model.request.Currency;
import io.tatum.model.response.eth.Log;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

/**
 * Endpoint doesn't work correctly. returns 500
 *
 * {{url}}/record?chain=ETH&id=0x3f852b9a17349cafb2b423b3c93ac2bc5791dd9478f849070bba6e8a367f7d3e
 * {{url}}/record?chain=ETH&id=0x8a238e7991efcd6729c8299aa47339208703cca231f07e2d10b55e9dd484adbf
 *
 */
@Disabled
public class RecordTest {
    @Test
    public void getLogRecordTest() throws ExecutionException, InterruptedException {
        Record record = new Record();
        Log log = record.getLogRecord(Currency.ETH, EthereumTest.ADDRESS);
        System.out.println(log);
    }
}
