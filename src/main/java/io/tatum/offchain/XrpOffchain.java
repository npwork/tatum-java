package io.tatum.offchain;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import io.tatum.blockchain.XRP;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferXrpOffchain;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.xrp.AccountData;
import io.tatum.transaction.xrp.TransactionJSON;
import io.tatum.transaction.xrp.XrpUtil;
import io.tatum.utils.ObjectValidator;
import io.xpring.xrpl.Signer;
import io.xpring.xrpl.Wallet;
import io.xpring.xrpl.XrpException;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Base58;
import org.xrpl.rpc.v1.Common.*;
import org.xrpl.rpc.v1.CurrencyAmount;
import org.xrpl.rpc.v1.Payment;
import org.xrpl.rpc.v1.Transaction;
import org.xrpl.rpc.v1.XRPDropsAmount;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.bitcoinj.core.Utils.HEX;

public class XrpOffchain {

    /**
     * Send Xrp transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendXrpOffchainTransaction(boolean testnet, TransferXrpOffchain body) throws ExecutionException, InterruptedException, IOException, XrpException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                var withdrawal = body.getWithdrawal();
                if (withdrawal.getFee() != null) {
                    withdrawal.setFee(new XRP().xrpGetFee().divide(BigDecimal.valueOf(1000000)).toString());
                }

                var acc = new XRP().xrpGetAccountInfo(body.getAccount());
                var withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
                String id = withdrawalResponse.getId();

                String txData;
                try {
                    txData = prepareXrpSignedOffchainTransaction(testnet,
                            withdrawal.getAmount(),
                            withdrawal.getAddress(),
                            body.getSecret(),
                            acc,
                            withdrawal.getFee(),
                            body.getSourceTag(),
                            withdrawal.getAttr());
                } catch (Exception e) {
                    e.printStackTrace();
                    Common.offchainCancelWithdrawal(id, true);
                    throw e;
                }

                return OffchainUtil.broadcast(txData, id, Currency.XRP.getCurrency());
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
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signXrpOffchainKMSTransaction(TransactionKMS tx, String secret) throws Exception {
        if (tx.getChain() != Currency.XRP) {
            throw new Exception("Unsupported chain.");
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
                var sequenceInt = accountDataInfo.getSequence();
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

    /**
     * Sign Xrp transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet        mainnet or testnet version
     * @param amount         amount to send
     * @param address        recipient address
     * @param secret         secret to sign transaction with
     * @param account        Xrp source account
     * @param fee            fee to pay
     * @param sourceTag      source tag to include in transaction
     * @param destinationTag
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareXrpSignedOffchainTransaction(boolean testnet, String amount, String address, String secret,
                                                      AccountData account, String fee, int sourceTag, String destinationTag) throws XrpException, ExecutionException, InterruptedException {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Destination destination = XrpUtil.createDestination(account.getAccount());

                CurrencyAmount paymentAmount = XrpUtil.createPayment(Long.valueOf(amount));
                Amount _amount = Amount.newBuilder().setValue(paymentAmount).build();
                SendMax sendMax = SendMax.newBuilder().setValue(paymentAmount).build();

                DestinationTag destTag = null;
                if (StringUtils.isNotEmpty(destinationTag)) {
                    destTag = DestinationTag.newBuilder().setValue(Integer.parseInt(destinationTag)).build();
                }

                Payment payment = Payment.newBuilder()
                        .setDestination(destination)
                        .setAmount(_amount)
                        .setSendMax(sendMax)
                        .setDestinationTag(destTag)
                        .build();

                XRPDropsAmount feeAmount = XRPDropsAmount.newBuilder().setDrops(Long.valueOf(fee)).build();
                Account _account = XrpUtil.createSenderAccount(address);
                Sequence sequence = Sequence.newBuilder().setValue(account.getSequence()).build();
                SourceTag _sourceTag = SourceTag.newBuilder().setValue(sourceTag).build();
                var maxLedgerVersion = account.getLedgerCurrentIndex() + 5;
                LastLedgerSequence lastLedgerSequence = LastLedgerSequence.newBuilder().setValue(maxLedgerVersion).build();

                Transaction transaction = Transaction.newBuilder()
                        .setAccount(_account)
                        .setFee(feeAmount)
                        .setSequence(sequence)
                        .setSourceTag(_sourceTag)
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
