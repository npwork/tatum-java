package io.tatum.offchain;

import com.google.common.base.Preconditions;
import io.tatum.ledger.LedgerAccount;
import io.tatum.ledger.LedgerVC;
import io.tatum.model.request.*;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.offchain.WithdrawalResponse;
import io.tatum.transaction.TronTx;
import io.tatum.utils.ObjectValidator;
import io.tatum.wallet.Address;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.CONTRACT_ADDRESSES;

public class TronOffchain {

    /**
     * Send Tron transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain or id of the withdrawal, if it was not cancelled automatically
     */
    public BroadcastResult sendTronOffchainTransaction(boolean testnet, TransferTrxOffchain body) throws ExecutionException, InterruptedException {
        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                String fromPriv;
                String mnemonic = body.getMnemonic();
                int index = body.getIndex();
                String fromPrivateKey = body.getFromPrivateKey();

                if (StringUtils.isNotEmpty(mnemonic) && index > 0) {
                    fromPriv = Address.generatePrivateKeyFromMnemonic(Currency.TRON, testnet, mnemonic, index);
                } else if (StringUtils.isNotEmpty(fromPrivateKey)) {
                    fromPriv = fromPrivateKey;
                } else {
                    throw new Exception("No mnemonic or private key is present.");
                }

                var withdrawal = body.getWithdrawal();
                String amount = withdrawal.getAmount();
                String address = withdrawal.getAddress();
                if (withdrawal.getFee() != null) {
                    withdrawal.setFee("1.5");
                }

                LedgerAccount ledgerAccount = new LedgerAccount();
                io.tatum.model.response.ledger.Account account = ledgerAccount.getAccountById(withdrawal.getSenderAccountId());

                String txData;
                if (account.getCurrency().equals(Currency.TRON)) {
                    TransferTron transferTron = new TransferTron();
                    transferTron.setAmount(amount);
                    transferTron.setFromPrivateKey(fromPriv);
                    transferTron.setTo(address);
                    txData = new TronTx().prepareTronSignedTransaction(testnet, transferTron);
                } else if (account.getCurrency().equals(Currency.USDT_TRON)) {
                    TransferTronTrc20 transferTronTrc20 = new TransferTronTrc20();
                    transferTronTrc20.setAmount(amount);
                    transferTronTrc20.setFromPrivateKey(fromPriv);
                    transferTronTrc20.setTo(address);
                    transferTronTrc20.setTokenAddress(CONTRACT_ADDRESSES.get(Currency.USDT_TRON));
                    transferTronTrc20.setFeeLimit(Long.valueOf(withdrawal.getFee()));
                    txData = new TronTx().prepareTronTrc20SignedTransaction(testnet, transferTronTrc20);
                } else {
                    var vc = new LedgerVC().getVirtualCurrencyByName(account.getCurrency());
                    if (vc.getTrcType().equals(TrcType.TRC10)) {
                        TransferTronTrc10 transferTronTrc10 = new TransferTronTrc10();
                        transferTronTrc10.setAmount(amount);
                        transferTronTrc10.setFromPrivateKey(fromPriv);
                        transferTronTrc10.setTo(address);
                        transferTronTrc10.setTokenId(vc.getErc20Address());
                        txData = new TronTx().prepareTronTrc10SignedTransaction(testnet, transferTronTrc10, vc.getPrecision());
                    } else if (vc.getTrcType().equals(TrcType.TRC20)) {
                        TransferTronTrc20 transferTronTrc20 = new TransferTronTrc20();
                        transferTronTrc20.setAmount(amount);
                        transferTronTrc20.setFeeLimit(Long.valueOf(withdrawal.getFee()));
                        transferTronTrc20.setTo(address);
                        transferTronTrc20.setTokenAddress(vc.getErc20Address());
                        txData = new TronTx().prepareTronTrc20SignedTransaction(testnet, transferTronTrc20);
                    } else {
                        throw new Exception("Unsupported account.");
                    }
                }

                WithdrawalResponse withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
                var id = withdrawalResponse.getId();
                try {
                    return OffchainUtil.broadcast(txData, id, Currency.TRON.getCurrency());
                } catch (Exception e) {
                    e.printStackTrace();
                    new BroadcastResult(null, id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).get();
    }
}
