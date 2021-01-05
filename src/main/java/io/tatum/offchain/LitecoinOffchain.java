package io.tatum.offchain;

import io.tatum.model.request.*;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.offchain.WithdrawalResponse;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import io.tatum.transaction.bitcoin.TransactionBuilder;
import io.tatum.utils.Convert;
import io.tatum.utils.ObjectValidator;
import io.tatum.wallet.Address;
import io.tatum.wallet.WalletGenerator;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Transaction;

import java.math.BigDecimal;
import java.util.Arrays;

import static io.tatum.constants.Constant.LITECOIN_MAINNET;
import static io.tatum.constants.Constant.LITECOIN_TESTNET;
import static org.bitcoinj.core.Utils.HEX;

public class LitecoinOffchain {

    /**
     * Send Litecoin transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendLitecoinOffchainTransaction(boolean testnet, TransferBtcBasedOffchain body) throws Exception {
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
            txData = prepareLitecoinSignedOffchainTransaction(testnet,
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
            broadcastWithdrawal.setCurrency(Currency.LTC.getCurrency());
            return new BroadcastResult(Common.offchainBroadcast(broadcastWithdrawal), id);

        } catch (Exception e) {
            e.printStackTrace();
            Common.offchainCancelWithdrawal(id, true);
            throw e;
        }
    }

    /**
     * Sign Litecoin pending transaction from Tatum KMS
     *
     * @param tx       pending transaction from KMS
     * @param mnemonic mnemonic to generate private keys to sign transaction with.
     * @param testnet  mainnet or testnet version
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signLitecoinOffchainKMSTransaction(TransactionKMS tx, String mnemonic, boolean testnet) throws Exception {
        WithdrawalResponseData[] withdrawalResponses = tx.getWithdrawalResponses();
        if (tx.getChain() != Currency.LTC || ArrayUtils.isEmpty(withdrawalResponses)) {
            throw new Exception("Unsupported chain.");
        }

        var network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;

        var builder = new TransactionBuilder(network);
        Transaction transaction = new Transaction(network, HEX.decode(tx.getSerializedTransaction()));
        String[] privateKeys = new String[withdrawalResponses.length];

        for (int i = 0; i < withdrawalResponses.length; i++) {
            if ("-1".equals(withdrawalResponses[i].getVIn())) {
                privateKeys[i] = null;
                continue;
            }
            int k = withdrawalResponses[i].getAddress() != null ? withdrawalResponses[i].getAddress().getDerivationKey() : 0;
            String privKey = new Address().generatePrivateKeyFromMnemonic(Currency.LTC, testnet, mnemonic, k);
            privateKeys[i] = privKey;
        }
        builder.fromTransaction(transaction, privateKeys);

        return builder.build().toHex();
    }

    /**
     * Sign Litecoin transaction with private keys locally. Nothing is broadcast to the blockchain.
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
    public String prepareLitecoinSignedOffchainTransaction(boolean testnet, WithdrawalResponseData[] data, String amount, String address, String mnemonic, KeyPair[] keyPair, String changeAddress) throws Exception {

        var network = testnet ? LITECOIN_TESTNET : LITECOIN_MAINNET;
        var tx = new TransactionBuilder(network);

        BigDecimal satoshis = Convert.toSatoshis(amount);
        tx.addOutput(address, satoshis);

        var lastVin = Arrays.stream(data).filter(d -> "-1".equals(d.getVIn())).findFirst().get();
        BigDecimal last = Convert.toSatoshis(lastVin.getAmount());

        Address _address = new Address();

        if (StringUtils.isNotEmpty(mnemonic)) {
            var xpub = WalletGenerator.generateBtcWallet(testnet, mnemonic).getXpub();
            tx.addOutput(_address.generateAddressFromXPub(Currency.LTC, testnet, xpub, 0), last);
        } else if (keyPair != null && StringUtils.isNotEmpty(changeAddress)) {
            tx.addOutput(changeAddress, last);
        } else {
            throw new Exception("Impossible to prepare transaction. Either mnemonic or keyPair and attr must be present.");
        }

        for (WithdrawalResponseData input : data) {
            if ("-1".equals(input.getVIn())) {
                if (StringUtils.isNotEmpty(mnemonic)) {
                    var derivationKey = input.getAddress() != null ? input.getAddress().getDerivationKey() : 0;
                    String privKey = _address.generatePrivateKeyFromMnemonic(Currency.LTC, testnet, mnemonic, derivationKey);
                    tx.addInput(input.getVIn(), input.getVInIndex(), privKey);
                } else if (keyPair != null) {
                    String privKey = Arrays.stream(keyPair).filter(k -> k.getAddress() == input.getAddress().getAddress()).findFirst().get().getPrivateKey();
                    tx.addInput(input.getVIn(), input.getVInIndex(), privKey);
                } else {
                    throw new Exception("Impossible to prepare transaction. Either mnemonic or keyPair and attr must be present.");
                }
            }
        }

        return tx.build().toHex();
    }

}
