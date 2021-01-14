package io.tatum.transaction;

import com.google.common.base.Preconditions;
import com.vechain.thorclient.clients.BlockchainClient;
import com.vechain.thorclient.clients.TransactionClient;
import com.vechain.thorclient.core.model.clients.*;
import com.vechain.thorclient.core.model.clients.base.AbstractToken;
import com.vechain.thorclient.utils.BytesUtils;
import com.vechain.thorclient.utils.CryptoUtils;
import com.vechain.thorclient.utils.Prefix;
import com.vechain.thorclient.utils.RawTransactionFactory;
import com.vechain.thorclient.utils.crypto.ECKeyPair;
import io.tatum.blockchain.VET;
import io.tatum.model.request.Currency;
import io.tatum.model.request.TransferVet;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.transaction.vet.TransactionJSON;
import io.tatum.utils.MapperFactory;
import io.tatum.utils.ObjectValidator;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.bitcoinj.core.Utils.HEX;

/**
 * The type Vet tx.
 */
public class VetTx {

    /**
     * Send VeChain transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the VeChain Server to connect to. If not set, default public server will be used.
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendVetTransaction(boolean testnet, TransferVet body, String provider) throws InterruptedException, ExecutionException, IOException {
        return new VET().vetBroadcast(prepareVetSignedTransaction(testnet, body, provider), null);
    }

    /**
     * Sign VeChain pending transaction from Tatum KMS
     *
     * @param tx             pending transaction from KMS
     * @param fromPrivateKey private key to sign transaction with.
     * @param testnet        mainnet or testnet version
     * @param provider       url of the VeChain Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws Exception the exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signVetKMSTransaction(TransactionKMS tx, String fromPrivateKey, boolean testnet, String provider) throws Exception {

        if (tx.getChain() != Currency.VET) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                TransactionJSON json = MapperFactory.get().readValue(HEX.decode(tx.getSerializedTransaction()), TransactionJSON.class);
                Amount amount = Amount.createFromToken(AbstractToken.VET);
                amount.setDecimalAmount(json.getAmount());
                ToClause clause = TransactionClient.buildVETToClause(Address.fromHexString(json.getTo()), amount, ToData.ZERO);
                RawTransaction rawTransaction = RawTransactionFactory.getInstance().createRawTransaction(
                        (byte) json.getChainTag(),
                        HEX.decode(json.getBlockRef()),
                        720,
                        21000,
                        (byte) 0x0,
                        HEX.decode(json.getNonce()),
                        clause);

                RawTransaction result = TransactionClient.sign(rawTransaction, ECKeyPair.create(fromPrivateKey));

                return BytesUtils.toHexString(result.encode(), Prefix.ZeroLowerX);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign VeChain transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the VeChain Server to connect to. If not set, default public server will be used.
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareVetSignedTransaction(boolean testnet, TransferVet body, String provider) throws ExecutionException, InterruptedException, IOException {

        Preconditions.checkArgument(ObjectValidator.isValidated(body));

        return CompletableFuture.supplyAsync(() -> {
            try {
                byte chainTag = BlockchainClient.getChainTag();
                byte[] blockRef = BlockchainClient.getBlockRef(Revision.BEST).toByteArray();
                Amount amount = Amount.createFromToken(AbstractToken.VET);
                amount.setDecimalAmount(body.getAmount());
                ToClause clause = TransactionClient.buildVETToClause(Address.fromHexString(body.getTo()), amount, ToData.ZERO);
                RawTransaction rawTransaction = RawTransactionFactory.getInstance().createRawTransaction(chainTag, blockRef, 720, 21000, (byte) 0x0, CryptoUtils.generateTxNonce(), clause);
                RawTransaction result = TransactionClient.sign(rawTransaction, ECKeyPair.create(body.getFromPrivateKey()));
                return BytesUtils.toHexString(result.encode(), Prefix.ZeroLowerX);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

}
