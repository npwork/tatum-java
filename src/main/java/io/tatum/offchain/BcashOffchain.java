package io.tatum.offchain;

import com.google.common.base.Preconditions;
import io.tatum.model.request.*;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.offchain.WithdrawalResponse;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import io.tatum.transaction.bcash.TransactionBuilder;
import io.tatum.utils.ObjectValidator;
import io.tatum.wallet.Address;
import io.tatum.wallet.WalletGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoincashj.core.Coin;
import org.bitcoincashj.core.Transaction;

import java.math.BigDecimal;
import java.util.Arrays;

import static io.tatum.constants.Constant.BCH_MAINNET;
import static io.tatum.constants.Constant.BCH_TESTNET;
import static org.bitcoincashj.core.Utils.HEX;

/**
 * The type Bitcoin offchain.
 */
@Log4j2
public class BcashOffchain {

    /**
     * Send Bitcoin Cash transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendBitcoinCashOffchainTransaction(boolean testnet, TransferBtcBasedOffchain body) throws Exception {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        CreateWithdrawal withdrawal = body.getWithdrawal();
        if (withdrawal.getFee() != null) {
            withdrawal.setFee("0.0005");
        }

        WithdrawalResponse withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
        var id = withdrawalResponse.getId();

        String txData;
        try {
            txData = prepareBitcoinCashSignedOffchainTransaction(testnet,
                    withdrawalResponse.getData(),
                    withdrawal.getAmount(),
                    withdrawal.getAddress(),
                    body.getMnemonic(),
                    body.getKeyPair(),
                    withdrawal.getAttr());
        } catch (Exception e) {
            e.printStackTrace();
            Common.offchainCancelWithdrawal(withdrawalResponse.getId(), true);
            throw e;
        }

        try {
            BroadcastWithdrawal broadcastWithdrawal = new BroadcastWithdrawal();
            broadcastWithdrawal.setTxData(txData);
            broadcastWithdrawal.setWithdrawalId(id);
            broadcastWithdrawal.setCurrency(Currency.BCH.getCurrency());
            return new BroadcastResult(Common.offchainBroadcast(broadcastWithdrawal), id);

        } catch (Exception e) {
            e.printStackTrace();
            Common.offchainCancelWithdrawal(id, true);
            throw e;
        }
    }

    /**
     * Sign Bitcoin Cash pending transaction from Tatum KMS
     *
     * @param tx       pending transaction from KMS
     * @param mnemonic mnemonic to generate private keys to sign transaction with.
     * @param testnet  mainnet or testnet version
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signBitcoinCashOffchainKMSTransaction(TransactionKMS tx, String mnemonic, boolean testnet) throws Exception {
        WithdrawalResponseData[] withdrawalResponses = tx.getWithdrawalResponses();
        if (tx.getChain() != Currency.BCH || ArrayUtils.isEmpty(withdrawalResponses)) {
            throw new Exception("Unsupported chain.");
        }

        var network = testnet ? BCH_TESTNET : BCH_MAINNET;

        var builder = new TransactionBuilder(network);
        Transaction transaction = new Transaction(network, HEX.decode(tx.getSerializedTransaction()));
        String[] privateKeys = new String[withdrawalResponses.length];
        Long[] amountsToSign = new Long[0];

        for (int i = 0; i < withdrawalResponses.length; i++) {
            if ("-1".equals(withdrawalResponses[i].getVIn())) {
                privateKeys[i] = null;
                continue;
            }
            int k = withdrawalResponses[i].getAddress() != null ? withdrawalResponses[i].getAddress().getDerivationKey() : 0;
            String privKey = new Address().generatePrivateKeyFromMnemonic(Currency.BCH, testnet, mnemonic, k);
            privateKeys[i] = privKey;
        }
        builder.fromTransaction(transaction, privateKeys, amountsToSign);

        return builder.build().toHex();
    }

    /**
     * Sign Bitcoin Cash transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet       mainnet or testnet version
     * @param data          data from Tatum system to prepare transaction from
     * @param amount        amount to send
     * @param address       recipient address
     * @param mnemonic      mnemonic to sign transaction from. mnemonic or keyPair must be present
     * @param keyPair       keyPair to sign transaction from. keyPair or mnemonic must be present
     * @param changeAddress address to send the rest of the unused coins
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareBitcoinCashSignedOffchainTransaction(boolean testnet, WithdrawalResponseData[] data, String amount,
                                                              String address, String mnemonic, KeyPair[] keyPair,
                                                              String changeAddress) throws Exception {

        Preconditions.checkArgument(StringUtils.isNotEmpty(mnemonic) || ArrayUtils.isNotEmpty(keyPair),
                "Impossible to prepare transaction. Either mnemonic or keyPair and attr must be present.");

        var network = testnet ? BCH_TESTNET : BCH_MAINNET;
        var tx = new TransactionBuilder(network);
        tx.addOutput(address, amount);

        var lastVin = Arrays.stream(data).filter(d -> "-1".equals(d.getVIn())).findFirst().get();
        String last = lastVin.getAmount();

        Address _address = new Address();

        if (StringUtils.isNotEmpty(mnemonic)) {
            var xpub = WalletGenerator.generateBchWallet(testnet, mnemonic).getXpub();
            tx.addOutput(_address.generateAddressFromXPub(Currency.BCH, testnet, xpub, 0), last);
            for (int i = 0; i < data.length; i++) {
                WithdrawalResponseData input = data[i];
                if (!"-1".equals(input.getVIn())) {
                    String value = input.getAmount();
                    var derivationKey = input.getAddress() != null ? input.getAddress().getDerivationKey() : 0;
                    String privKey = _address.generatePrivateKeyFromMnemonic(Currency.BCH, testnet, mnemonic, derivationKey);
                    tx.addInput(input.getVIn(), input.getVInIndex(), privKey, value);
                }
            }
        } else if (keyPair != null && StringUtils.isNotEmpty(changeAddress)) {
            tx.addOutput(changeAddress, last);
            for (int i = 0; i < data.length; i++) {
                WithdrawalResponseData input = data[i];
                if (!"-1".equals(input.getVIn())) {
                    String value = input.getAmount();
                    String privKey = Arrays.stream(keyPair).filter(k -> k.getAddress().equals(input.getAddress().getAddress())).findFirst().get().getPrivateKey();
                    if (StringUtils.isNotEmpty(privKey)) {
                        tx.addInput(input.getVIn(), input.getVInIndex(), privKey, value);
                    }
                }
            }
        }

        return tx.build().toHex();
    }
}
