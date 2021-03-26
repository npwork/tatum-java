package io.tatum.offchain;

import com.google.common.base.Preconditions;
import io.tatum.model.request.*;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.model.response.offchain.BroadcastResult;
import io.tatum.model.response.offchain.WithdrawalResponse;
import io.tatum.model.response.offchain.WithdrawalResponseData;
import io.tatum.transaction.bitcoin.TransactionBuilder;
import io.tatum.utils.ObjectValidator;
import io.tatum.wallet.Address;
import io.tatum.wallet.WalletGenerator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static io.tatum.constants.Constant.BITCOIN_MAINNET;
import static io.tatum.constants.Constant.BITCOIN_TESTNET;
import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Bitcoin offchain.
 */
@Log4j2
public class BitcoinOffchain {

    /**
     * Send Bitcoin transaction from Tatum Ledger account to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet mainnet or testnet version
     * @param body    content of the transaction to broadcast
     * @return the broadcast result
     * @throws Exception the exception
     * @returns transaction id of the transaction in the blockchain
     */
    public BroadcastResult sendBitcoinOffchainTransaction(boolean testnet, TransferBtcBasedOffchain body) throws Exception {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        CreateWithdrawal withdrawal = body.getWithdrawal();
        if (withdrawal.getFee() == null) {
            withdrawal.setFee("0.0005");
        }

        WithdrawalResponse withdrawalResponse = Common.offchainStoreWithdrawal(withdrawal);
        var id = withdrawalResponse.getId();

        String txData;
        try {
            txData = prepareBitcoinSignedOffchainTransaction(testnet,
                    withdrawalResponse.getData(),
                    withdrawal.getAmount(),
                    withdrawal.getAddress(),
                    body.getMnemonic(),
                    body.getKeyPair(),
                    withdrawal.getAttr(), withdrawal.getMultipleAmounts());
        } catch (Exception e) {
            e.printStackTrace();
            Common.offchainCancelWithdrawal(id, true);
            throw e;
        }

        try {
            BroadcastWithdrawal broadcastWithdrawal = new BroadcastWithdrawal();
            broadcastWithdrawal.setTxData(txData);
            broadcastWithdrawal.setWithdrawalId(id);
            broadcastWithdrawal.setCurrency(Currency.BTC.getCurrency());
            return new BroadcastResult(Common.offchainBroadcast(broadcastWithdrawal), id);

        } catch (Exception e) {
            e.printStackTrace();
            Common.offchainCancelWithdrawal(id, true);
            throw e;
        }
    }

    /**
     * Sign Bitcoin pending transaction from Tatum KMS
     *
     * @param tx       pending transaction from KMS
     * @param mnemonic mnemonic to generate private keys to sign transaction with.
     * @param testnet  mainnet or testnet version
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signBitcoinOffchainKMSTransaction(TransactionKMS tx, String mnemonic, boolean testnet) throws Exception {
        WithdrawalResponseData[] withdrawalResponses = tx.getWithdrawalResponses();
        if (tx.getChain() != Currency.BTC || ArrayUtils.isEmpty(withdrawalResponses)) {
            throw new Exception("Unsupported chain.");
        }

        var network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;

        var builder = new TransactionBuilder(network);
        Transaction transaction = new Transaction(network, HEX.decode(tx.getSerializedTransaction()));
        String[] privateKeys = new String[withdrawalResponses.length];

        for (int i = 0; i < withdrawalResponses.length; i++) {
            if ("-1".equals(withdrawalResponses[i].getVIn())) {
                privateKeys[i] = null;
                continue;
            }
            int k = withdrawalResponses[i].getAddress() != null ? withdrawalResponses[i].getAddress().getDerivationKey() : 0;
            String privKey = Address.generatePrivateKeyFromMnemonic(Currency.BTC, testnet, mnemonic, k);
            privateKeys[i] = privKey;
        }
        builder.fromTransaction(transaction, privateKeys);

        return builder.build().toHex();
    }

    /**
     * Sign Bitcoin transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet       mainnet or testnet version
     * @param data          data from Tatum system to prepare transaction from
     * @param amount        amount to send
     * @param address       recipient address
     * @param mnemonic      mnemonic to sign transaction from. mnemonic or keyPair must be present
     * @param keyPair       keyPair to sign transaction from. keyPair or mnemonic must be present
     * @param changeAddress address to send the rest of the unused coins
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareBitcoinSignedOffchainTransaction(boolean testnet, WithdrawalResponseData[] data,
                                                          String amount, String address, String mnemonic,
                                                          KeyPair[] keyPair, String changeAddress,
                                                          String[] multipleAmounts) throws Exception {

        return prepareBitcoinSignedOffchainTransaction(testnet, data, amount, address, mnemonic, keyPair, changeAddress,
                multipleAmounts, false);
    }

    /**
     * Sign Bitcoin transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet         mainnet or testnet version
     * @param data            data from Tatum system to prepare transaction from
     * @param amount          amount to send
     * @param address         recipient address, if multiple recipients are present, it should be string separated by ','
     * @param mnemonic        mnemonic to sign transaction from. mnemonic or keyPair must be present
     * @param keyPair         keyPair to sign transaction from. keyPair or mnemonic must be present
     * @param changeAddress   address to send the rest of the unused coins
     * @param multipleAmounts if multiple recipients are present in the address separated by ',', this should be list of amounts to send
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareBitcoinSignedOffchainTransaction(boolean testnet, WithdrawalResponseData[] data, String amount,
                                                          String address, String mnemonic, KeyPair[] keyPair,
                                                          String changeAddress, String[] multipleAmounts,
                                                          boolean bech32) throws Exception {

        Preconditions.checkArgument(StringUtils.isNotEmpty(mnemonic) || ArrayUtils.isNotEmpty(keyPair),
                "Impossible to prepare transaction. Either mnemonic or keyPair and attr must be present.");

        NetworkParameters network = testnet ? BITCOIN_TESTNET : BITCOIN_MAINNET;
        var tx = new TransactionBuilder(network);

        if (ArrayUtils.isNotEmpty(multipleAmounts)) {
            final String[] addresses = StringUtils.split(address, ',');
            for (int i = 0; i < multipleAmounts.length; i++) {
                tx.addOutput(addresses[i], multipleAmounts[i]);
            }
        } else {
            tx.addOutput(address, amount);
        }

        var lastVin = Arrays.stream(data).filter(d -> "-1".equals(d.getVIn())).findFirst();
        String last = lastVin.isPresent() ? lastVin.get().getAmount() : "0";

        if (new BigDecimal(last).compareTo(BigDecimal.ZERO) > 0) {
            if (StringUtils.isNotEmpty(mnemonic) && StringUtils.isEmpty(changeAddress)) {
                var xpub = WalletGenerator.generateWallet(Currency.BTC, testnet, mnemonic).getXpub();
                tx.addOutput(Address.generateAddressFromXPub(Currency.BTC, testnet, xpub, 0), last);
            } else if (StringUtils.isNotEmpty(changeAddress)) {
                tx.addOutput(changeAddress, last, bech32);
            } else {
                throw new Exception("Impossible to prepare transaction. Either mnemonic or keyPair and attr must be present.");
            }
        }

        for (WithdrawalResponseData input : data) {
            if (!"-1".equals(input.getVIn())) {
                if (StringUtils.isNotEmpty(mnemonic)) {
                    var derivationKey = input.getAddress() != null ? input.getAddress().getDerivationKey() : 0;
                    String privKey = Address.generatePrivateKeyFromMnemonic(Currency.BTC, testnet, mnemonic, derivationKey);
                    tx.addInput(input.getVIn(), input.getVInIndex(), privKey);
                } else if (ArrayUtils.isNotEmpty(keyPair)) {
                    Optional<KeyPair> pair = getKeyPairByAddress(keyPair, input.getAddress().getAddress());
                    if (pair.isPresent()) {
                        String privKey = pair.get().getPrivateKey();
                        tx.addInput(input.getVIn(), input.getVInIndex(), privKey);
                    }
                } else {
                    throw new Exception("Impossible to prepare transaction. Either mnemonic or keyPair and attr must be present.");
                }
            }
        }

        return tx.build().toHex();
    }

    @NotNull
    private Optional<KeyPair> getKeyPairByAddress(KeyPair[] keyPair, String address) {
        return Arrays.stream(keyPair)
                .filter(k -> k != null && k.getAddress().equals(address))
                .findFirst();
    }
}
