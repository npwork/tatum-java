package io.tatum.offchain;

import com.google.common.base.Preconditions;
import io.tatum.blockchain.XLM;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferXlmOffchain;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.offchain.WithdrawalResponse;
import io.tatum.utils.ObjectValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.stellar.sdk.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * The type Xlm offchain.
 */
public class XlmOffchain {

    /**
     * Send Stellar transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the broadcast result
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendXlmOffchainTransaction(boolean testnet, TransferXlmOffchain body) throws ExecutionException, InterruptedException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {

                var withdrawal = body.getWithdrawal();

                if (withdrawal.getFee() != null) {
                    withdrawal.setFee("0.00001");
                }

                String message = withdrawal.getAttr();
                Memo memo = StringUtils.isNotEmpty(message) ? message.length() > 20 ? Memo.hash(message) : Memo.text(message)
                        : Memo.text(Strings.EMPTY);

                KeyPair keyPair = KeyPair.fromSecretSeed(body.getSecret());
                var fromAccount = keyPair.getAccountId();

                var sequence = new XLM().xlmGetAccountInfo(fromAccount);
                Account account = new Account(fromAccount, sequence.getSequence());

                WithdrawalResponse withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
                var id = withdrawalResponse.getId();

                String tx;
                try {
                    tx = prepareXlmSignedOffchainTransaction(
                            testnet,
                            account,
                            withdrawal.getAmount(),
                            withdrawal.getAddress(),
                            body.getSecret(),
                            memo);
                } catch (Exception e) {
                    e.printStackTrace();
                    Common.offchainCancelWithdrawal(id, true);
                    throw e;
                }
                return OffchainUtil.broadcast(tx, id, Currency.XLM.getCurrency());

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Stellar pending transaction from Tatum KMS
     *
     * @param tx      pending transaction from KMS
     * @param secret  secret key to sign transaction with.
     * @param testnet mainnet or testnet version
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signXlmOffchainKMSTransaction(TransactionKMS tx, String secret, boolean testnet) throws Exception {
        if (tx.getChain() != Currency.XLM) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                Network network = testnet ? Network.TESTNET : Network.PUBLIC;
                Transaction parsedTx = (Transaction) Transaction.fromEnvelopeXdr(tx.getSerializedTransaction(), network);
                parsedTx.sign(KeyPair.fromSecretSeed(secret));
                return parsedTx.toEnvelopeXdrBase64();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();


    }

    /**
     * Sign Stellar transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet mainnet or testnet version
     * @param account Stellar account with information
     * @param amount  amount to send
     * @param address recipient address
     * @param secret  secret to sign transaction with
     * @param memo    short memo to include in transaction
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareXlmSignedOffchainTransaction(boolean testnet, Account account, String amount, String address,
                                                      String secret, Memo memo) throws ExecutionException, InterruptedException {

        return CompletableFuture.supplyAsync(() -> {
            try {
                KeyPair keyPair = KeyPair.fromSecretSeed(secret);

                Network network = testnet ? Network.TESTNET : Network.PUBLIC;
                var builder = new Transaction.Builder(account, network)
                        .setBaseFee(100)
                        .addMemo(memo)
                        .setTimeout(30);

                var tx = builder.addOperation(new PaymentOperation.Builder(
                        address,
                        new AssetTypeNative(),
                        amount)
                        .build()).build();

                tx.sign(keyPair);

                return tx.toEnvelopeXdrBase64();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

}
