package io.tatum.offchain;

import io.tatum.model.request.BroadcastWithdrawal;
import io.tatum.model.response.common.TxHash;
import io.tatum.model.response.offchain.BroadcastResult;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * The type Offchain util.
 */
public class OffchainUtil {

    private OffchainUtil() {
    }

    /**
     * Broadcast broadcast result.
     *
     * @param tx       the tx
     * @param id       the id
     * @param currency the currency
     * @return the broadcast result
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    public static BroadcastResult broadcast(String tx, String id, String currency) throws ExecutionException, InterruptedException, IOException {
        try {
            BroadcastWithdrawal broadcastWithdrawal = new BroadcastWithdrawal();
            broadcastWithdrawal.setTxData(tx);
            broadcastWithdrawal.setWithdrawalId(id);
            broadcastWithdrawal.setCurrency(currency);
            TxHash txHash = Common.offchainBroadcast(broadcastWithdrawal);
            return new BroadcastResult(txHash, id);
        } catch (Exception e) {
            e.printStackTrace();
            Common.offchainCancelWithdrawal(id, true);
            throw e;
        }
    }

}
