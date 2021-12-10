package io.tatum.transaction;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import io.tatum.blockchain.XRP;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferXrp;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.xrp.AccountData;
import io.tatum.transaction.xrp.TransactionJSON;
import io.tatum.transaction.xrp.XrpUtil;
import io.tatum.utils.ObjectValidator;
import io.xpring.xrpl.Signer;
import io.xpring.xrpl.Wallet;
import lombok.extern.log4j.Log4j2;
import org.bitcoinj.core.Base58;
import org.xrpl.rpc.v1.Common.*;
import org.xrpl.rpc.v1.CurrencyAmount;
import org.xrpl.rpc.v1.Payment;
import org.xrpl.rpc.v1.Transaction;
import org.xrpl.rpc.v1.XRPDropsAmount;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Xrp tx.
 */
@Log4j2
public class XrpTx {

    /**
     * Send Xrp transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param body content of the transaction to broadcast
     * @return the transaction hash
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendXrpTransaction(TransferXrp body) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return new XRP().xrpBroadcast(prepareXrpSignedTransaction(body), null);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Xrp transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body content of the transaction to broadcast
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareXrpSignedTransaction(TransferXrp body) throws ExecutionException, InterruptedException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                var fromAccount = body.getFromAccount();

                XRP xrp = new XRP();
                var fee = body.getFee() != null && !body.getFee().equals(0) ?
                        body.getFee() : xrp.xrpGetFee().divide(BigDecimal.valueOf(1000000));

                XRPDropsAmount feeAmount = XRPDropsAmount.newBuilder().setDrops(fee.longValue()).build();

                Account account = XrpUtil.createSenderAccount(fromAccount);
                CurrencyAmount paymentAmount = XrpUtil.createPayment(body.getAmount().longValue());
                Amount amount = XrpUtil.createSenderAmount(paymentAmount);
                Destination destination = XrpUtil.createDestination(body.getTo());
                SendMax sendMax = SendMax.newBuilder().setValue(paymentAmount).build();

                AccountData accountDataInfo = xrp.xrpGetAccountInfo(fromAccount);

                var sequenceInt = accountDataInfo.getDetails().getSequence();
                Sequence sequence = Sequence.newBuilder().setValue(sequenceInt).build();
                var maxLedgerVersion = accountDataInfo.getLedgerCurrentIndex() + 5;
                LastLedgerSequence lastLedgerSequence = LastLedgerSequence.newBuilder().setValue(maxLedgerVersion).build();
                SourceTag sourceTag = SourceTag.newBuilder().setValue(body.getSourceTag()).build();
                DestinationTag destinationTag = DestinationTag.newBuilder().setValue(body.getDestinationTag()).build();

                Payment payment = Payment.newBuilder()
                        .setDestination(destination)
                        .setAmount(amount)
                        .setSendMax(sendMax)
                        .setDestinationTag(destinationTag)
                        .build();

                Transaction transaction = Transaction.newBuilder()
                        .setAccount(account)
                        .setFee(feeAmount)
                        .setSequence(sequence)
                        .setSourceTag(sourceTag)
                        .setLastLedgerSequence(lastLedgerSequence)
                        .setPayment(payment)
                        .build();

                Wallet wallet = new Wallet(body.getFromSecret());
                return HEX.encode(Signer.signTransaction(transaction, wallet));

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }).get();
    }

    /**
     * Sign Xrp pending transaction from Tatum KMS
     *
     * @param tx     pending transaction from KMS
     * @param secret secret key to sign transaction with.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signXrpKMSTransaction(TransactionKMS tx, String secret) throws ExecutionException, InterruptedException {
        if (tx.getChain() != Currency.XRP) {
            log.error("Unsupported chain.");
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                TransactionJSON txJSON = objectMapper.readValue(HEX.decode(tx.getSerializedTransaction()), TransactionJSON.class);

                XRPDropsAmount feeAmount = XRPDropsAmount.newBuilder().setDrops(txJSON.getFee()).build();

                Account account = XrpUtil.createSenderAccount(txJSON.getAccount());
                CurrencyAmount paymentAmount = XrpUtil.createPayment(txJSON.getAmount());
                Amount amount = XrpUtil.createSenderAmount(paymentAmount);
                SendMax sendMax = SendMax.newBuilder().setValue(paymentAmount).build();
                Destination destination = XrpUtil.createDestination(txJSON.getDestination());

                AccountData accountDataInfo = new XRP().xrpGetAccountInfo(txJSON.getAccount());

                var sequenceInt = accountDataInfo.getDetails().getSequence();
                Sequence sequence = Sequence.newBuilder().setValue(sequenceInt).build();
                var maxLedgerVersion = accountDataInfo.getLedgerCurrentIndex() + 5;
                LastLedgerSequence lastLedgerSequence = LastLedgerSequence.newBuilder().setValue(maxLedgerVersion).build();

                Payment payment = Payment.newBuilder()
                        .setDestination(destination)
                        .setAmount(amount)
                        .setSendMax(sendMax)
                        .build();

                Transaction transaction = Transaction.newBuilder()
                        .setAccount(account)
                        .setFee(feeAmount)
                        .setSequence(sequence)
                        .setLastLedgerSequence(lastLedgerSequence)
                        .setPayment(payment)
                        .build();

                Wallet wallet = new Wallet(secret);
                return HEX.encode(Signer.signTransaction(transaction, wallet));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }).get();
    }

}
