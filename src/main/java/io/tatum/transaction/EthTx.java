package io.tatum.transaction;

import io.tatum.blockchain.Ethereum;
import io.tatum.contracts.erc20.TokenBytecode;
import io.tatum.model.request.*;
import io.tatum.model.response.common.TransactionHash;
import io.tatum.model.response.kms.TransactionKMS;
import io.tatum.transaction.eth.EthUtil;
import io.tatum.transaction.eth.Web3jClient;
import io.tatum.utils.Async;
import io.tatum.utils.MapperFactory;
import io.tatum.utils.ObjectValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.CONTRACT_ADDRESSES;
import static io.tatum.constants.Constant.CONTRACT_DECIMALS;
import static io.tatum.model.request.Currency.ETH;
import static org.web3j.utils.Convert.Unit.ETHER;
import static org.web3j.utils.Convert.Unit.GWEI;

@Log4j2
public class EthTx {

    /**
     * Sign Ethereum pending transaction from Tatum KMS
     *
     * @param tx             pending transaction from KMS
     * @param fromPrivateKey private key to sign transaction with.
     * @param testnet        mainnet or testnet version
     * @param provider       url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String signEthKMSTransaction(TransactionKMS tx, String fromPrivateKey, boolean testnet, String provider) throws Exception {
        if (tx.getChain() != Currency.ETH) {
            throw new Exception("Unsupported chain.");
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                Web3j web3j = Web3jClient.get(provider);
                Credentials credentials = Credentials.create(fromPrivateKey);

                Transaction prepareTx = MapperFactory.get().readValue(tx.getSerializedTransaction(), Transaction.class);

                EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(prepareTx).send();
                BigInteger gasLimit = ethEstimateGas.getAmountUsed().add(BigInteger.valueOf(5000));

                RawTransaction rawTransaction = RawTransaction.createTransaction(new BigInteger(prepareTx.getNonce()),
                        new BigInteger(prepareTx.getGasPrice()), gasLimit,
                        prepareTx.getTo(),
                        new BigInteger(prepareTx.getValue()),
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign Ethereum Store data transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareStoreDataTransaction(CreateRecord body, String provider) throws ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _to = body.getTo();
                var _ethFee = body.getEthFee();
                var _data = body.getData();
                var _nonce = body.getNonce();

                Web3j web3j = Web3jClient.get(provider);
                Credentials credentials = Credentials.create(body.getFromPrivateKey());
                var address = StringUtils.isNoneEmpty(_to) ? _to : credentials.getAddress();

                Ethereum ethereum = new Ethereum();
                var nonce = (_nonce != null && _nonce.compareTo(BigInteger.ONE) > 0) ? _nonce :
                        ethereum.ethGetTransactionsCount(address);

                BigInteger gasLimit;
                BigInteger gasPrice;

                if (_ethFee != null) {
                    gasLimit = new BigInteger(_ethFee.getGasLimit());
                    gasPrice = new BigInteger(_ethFee.getGasPrice());
                } else {
                    gasLimit = BigInteger.valueOf(_data.length() * 68 + 21000);
                    gasPrice = Convert.fromWei(ethGetGasPriceInWei(), GWEI).toBigInteger();
                }

                String data = EthUtil.toHexString(_data);

                RawTransaction rawTransaction = RawTransaction.createTransaction(nonce,
                        gasPrice,
                        gasLimit,
                        _to,
                        BigInteger.ZERO,
                        data);

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Ethereum or supported ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareEthOrErc20SignedTransaction(TransferEthErc20 body, String provider) throws ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _to = body.getTo();
                var _fee = body.getFee();
                var _data = body.getData();
                var _nonce = body.getNonce();
                var _currency = body.getCurrency();
                var _amount = body.getAmount();

                Web3j web3j = Web3jClient.get(provider);
                Credentials credentials = Credentials.create(body.getFromPrivateKey());
                BigInteger gasPrice = _fee != null ? new BigInteger(_fee.getGasPrice()) :
                        Convert.fromWei(ethGetGasPriceInWei(), GWEI).toBigInteger();
                String data = EthUtil.toHexString(_data);

                Transaction prepareTx;
                BigInteger amount;

                if (_currency == ETH) {
                    amount = Convert.toWei(_amount, ETHER).toBigInteger();
                    prepareTx = new Transaction(StringUtils.EMPTY, _nonce, gasPrice, null, _to, amount, data);
                } else {
                    String contractAddress = CONTRACT_ADDRESSES.get(_currency.getCurrency());
                    var digits = BigInteger.TEN.pow(CONTRACT_DECIMALS.get(_currency.getCurrency()));
                    amount = new BigDecimal(_amount).toBigInteger().multiply(digits);

                    Function function = new Function(
                            "transfer",  // function we're calling
                            Arrays.asList(new Address(_to), new Uint256(amount)),  // Parameters to pass as Solidity Types
                            Arrays.asList(new org.web3j.abi.TypeReference<Bool>() {
                            }));
                    String txData = FunctionEncoder.encode(function);

                    prepareTx = new Transaction(StringUtils.EMPTY,
                            _nonce,
                            gasPrice,
                            null,
                            contractAddress,
                            amount,
                            txData);
                }

                BigInteger gasLimit = _fee != null ? new BigInteger(_fee.getGasLimit()) : EthUtil.estimateGas(web3j, prepareTx);

                RawTransaction rawTransaction = RawTransaction.createTransaction(_nonce,
                        gasPrice, gasLimit,
                        prepareTx.getTo(),
                        amount,
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();
    }

    /**
     * Sign Ethereum custom ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareCustomErc20SignedTransaction(TransferCustomErc20 body, String provider) throws ExecutionException, InterruptedException, IOException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _to = body.getTo();
                var _fee = body.getFee();
                var _nonce = body.getNonce();
                var _amount = body.getAmount();
                var _contractAddress = body.getContractAddress();
                var _digits = body.getDigits();

                Web3j web3j = Web3jClient.get(provider);

                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                BigInteger gasLimit;
                BigInteger gasPrice;
                if (_fee != null) {
                    gasPrice = new BigInteger(_fee.getGasPrice());
                } else {
                    gasPrice = Convert.fromWei(ethGetGasPriceInWei(), GWEI).toBigInteger();
                }

                var decimals = BigInteger.TEN.pow(_digits);
                var amount = new BigDecimal(_amount).toBigInteger().multiply(decimals);

                Function function = new Function(
                        "transfer",  // function we're calling
                        Arrays.asList(new Address(_to), new Uint256(amount)),  // Parameters to pass as Solidity Types
                        Arrays.asList(new org.web3j.abi.TypeReference<Bool>() {
                        }));
                String txData = FunctionEncoder.encode(function);

                Transaction prepareTx = new Transaction(StringUtils.EMPTY,
                        _nonce,
                        gasPrice,
                        null,
                        _contractAddress,
                        amount,
                        txData);

                if (_fee != null) {
                    gasLimit = new BigInteger(_fee.getGasLimit());
                } else {
                    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(prepareTx).send();
                    gasLimit = ethEstimateGas.getAmountUsed().add(BigInteger.valueOf(5000));
                }

                RawTransaction rawTransaction = RawTransaction.createTransaction(
                        _nonce,
                        gasPrice,
                        gasLimit,
                        prepareTx.getTo(),
                        amount,
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Sign Ethereum deploy ERC20 transaction with private keys locally. Nothing is broadcast to the blockchain.
     *
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction data to be broadcast to blockchain.
     */
    public String prepareDeployErc20SignedTransaction(DeployEthErc20 body, String provider) throws ExecutionException, InterruptedException, IOException {
        if (!ObjectValidator.isValidated(body)) {
            return null;
        }

        return CompletableFuture.supplyAsync(() -> {
            try {
                var _fee = body.getFee();
                var _nonce = body.getNonce();

                Web3j web3j = Web3jClient.get(provider);
                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                BigInteger gasPrice = _fee != null ? new BigInteger(_fee.getGasPrice()) :
                        Convert.fromWei(ethGetGasPriceInWei(), GWEI).toBigInteger();

                var cap = new BigDecimal(body.getSupply()).toBigInteger().multiply(BigInteger.TEN.pow(body.getDigits()));

                String encodedConstructor = FunctionEncoder.encodeConstructor(
                        Arrays.<Type>asList(
                                new Utf8String(body.getName()),
                                new Utf8String(body.getSymbol()),
                                new Address(body.getAddress()),
                                new Uint8(body.getDigits()),
                                new Uint256(cap),
                                new Uint256(cap)));

                Transaction prepareTx = new Transaction(StringUtils.EMPTY,
                        _nonce,
                        gasPrice,
                        null,
                        StringUtils.EMPTY,
                        BigInteger.ZERO,
                        TokenBytecode.TOKEN_BYTE_CODE + encodedConstructor);

                BigInteger gasLimit = _fee != null ? new BigInteger(_fee.getGasLimit()) : EthUtil.estimateGas(web3j, prepareTx);

                RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                        _nonce,
                        gasPrice,
                        gasLimit,
                        BigInteger.ZERO,
                        prepareTx.getData());

                byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
                return Numeric.toHexString(signedMessage);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).get();

    }

    /**
     * Estimate Gas price for the transaction.
     */
    public BigDecimal ethGetGasPriceInWei() throws ExecutionException, InterruptedException {
        var data = Async.getJson("https://ethgasstation.info/json/ethgasAPI.json");
        JSONObject jsonObject = new JSONObject(data);
        return Convert.toWei(jsonObject.getBigDecimal("fast").divide(BigDecimal.TEN), GWEI);
    }

    /**
     * Send Ethereum store data transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendStoreDataTransaction(boolean testnet, CreateRecord body, String provider) throws ExecutionException, InterruptedException, IOException {
        return new Ethereum().ethBroadcast(prepareStoreDataTransaction(body, provider), null);
    }

    /**
     * Send Ethereum or supported ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendEthOrErc20Transaction(boolean testnet, TransferEthErc20 body, String provider) throws ExecutionException, InterruptedException, IOException {
        return new Ethereum().ethBroadcast(prepareEthOrErc20SignedTransaction(body, provider), null);
    }

    /**
     * Send Ethereum custom ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendCustomErc20Transaction(boolean testnet, TransferCustomErc20 body, String provider) throws InterruptedException, ExecutionException, IOException {
        return new Ethereum().ethBroadcast(prepareCustomErc20SignedTransaction(body, provider), null);
    }

    /**
     * Send Ethereum deploy ERC20 transaction to the blockchain. This method broadcasts signed transaction to the blockchain.
     * This operation is irreversible.
     *
     * @param testnet  mainnet or testnet version
     * @param body     content of the transaction to broadcast
     * @param provider url of the Ethereum Server to connect to. If not set, default public server will be used.
     * @returns transaction id of the transaction in the blockchain
     */
    public TransactionHash sendDeployErc20Transaction(boolean testnet, DeployEthErc20 body, String provider) throws InterruptedException, ExecutionException, IOException {
        return new Ethereum().ethBroadcast(prepareDeployErc20SignedTransaction(body, provider), null);
    }
}
