package io.tatum.offchain;

import io.tatum.model.request.BroadcastWithdrawal;
import io.tatum.model.response.common.TxHash;
import io.tatum.model.response.offchain.BroadcastResult;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class OffchainUtil {

    private OffchainUtil() {
    }

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
