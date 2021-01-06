package io.tatum.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.BaseEncoding;
import io.tatum.blockchain.XRP;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferXrp;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.xrp.AccountData;
import io.tatum.transaction.xrp.TransactionJSON;
import io.tatum.utils.ObjectValidator;
import io.xpring.xrpl.Signer;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.XrpException;
import lombok.extern.log4j.Log4j2;
import org.xrpl.rpc.v1.*;
import org.xrpl.rpc.v1.Common.Account;
import org.xrpl.rpc.v1.Common.Amount;
import org.xrpl.rpc.v1.Common.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var fromAccount = body.getFromAccount();

                XRP xrp = new XRP();
                var fee = body.getFee() != null && !body.getFee().equals(0) ?
                        body.getFee() : xrp.xrpGetFee().divide(BigDecimal.valueOf(1000000));

                XRPDropsAmount feeAmount = XRPDropsAmount.newBuilder().setDrops(fee.longValue()).build();

                AccountAddress senderAddress = AccountAddress.newBuilder().setAddress(fromAccount).build();
                Account account = Account.newBuilder().setValue(senderAddress).build();

                XRPDropsAmount sendAmount = XRPDropsAmount.newBuilder().setDrops(body.getAmount().longValue()).build();
                CurrencyAmount paymentAmount = CurrencyAmount.newBuilder().setXrpAmount(sendAmount).build();
                Amount amount = Amount.newBuilder().setValue(paymentAmount).build();

                AccountAddress destinationAddress = AccountAddress.newBuilder().setAddress(body.getTo()).build();
                Destination destination = Destination.newBuilder().setValue(destinationAddress).build();

                AccountData accountDataInfo = xrp.xrpGetAccountInfo(fromAccount);

                var sequenceInt = accountDataInfo.getSequence();
                Sequence sequence = Sequence.newBuilder().setValue(sequenceInt).build();

                var maxLedgerVersion = accountDataInfo.getLedgerCurrentIndex() + 5;
                LastLedgerSequence lastLedgerSequence = LastLedgerSequence.newBuilder().setValue(maxLedgerVersion).build();

                SendMax sendMax = SendMax.newBuilder().setValue(paymentAmount).build();

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
                return BaseEncoding.base16().lowerCase().encode(Signer.signTransaction(transaction, wallet));

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
                XRP xrp = new XRP();
                Wallet wallet = new Wallet(secret);
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

                TransactionJSON txJSON = objectMapper.readValue(tx.getSerializedTransaction(), TransactionJSON.class);

                XRPDropsAmount feeAmount = XRPDropsAmount.newBuilder().setDrops(txJSON.getFee()).build();

                AccountAddress senderAddress = AccountAddress.newBuilder().setAddress(txJSON.getAccount()).build();
                Account account = Account.newBuilder().setValue(senderAddress).build();

                XRPDropsAmount sendAmount = XRPDropsAmount.newBuilder().setDrops(txJSON.getAmount()).build();
                CurrencyAmount paymentAmount = CurrencyAmount.newBuilder().setXrpAmount(sendAmount).build();
                Amount amount = Amount.newBuilder().setValue(paymentAmount).build();

                AccountAddress destinationAddress = AccountAddress.newBuilder().setAddress(txJSON.getDestination()).build();
                Destination destination = Destination.newBuilder().setValue(destinationAddress).build();

                AccountData accountDataInfo = xrp.xrpGetAccountInfo(txJSON.getAccount());

                var sequenceInt = accountDataInfo.getSequence();
                Sequence sequence = Sequence.newBuilder().setValue(sequenceInt).build();

                var maxLedgerVersion = accountDataInfo.getLedgerCurrentIndex() + 5;
                LastLedgerSequence lastLedgerSequence = LastLedgerSequence.newBuilder().setValue(maxLedgerVersion).build();

                SendMax sendMax = SendMax.newBuilder().setValue(paymentAmount).build();

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


                return BaseEncoding.base16().lowerCase().encode(Signer.signTransaction(transaction, wallet));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }).get();
    }

}
