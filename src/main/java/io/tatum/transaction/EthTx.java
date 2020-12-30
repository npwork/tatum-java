package io.tatum.transaction;

import io.tatum.blockchain.Ethereum;
import io.tatum.model.request.CreateRecord;
import io.tatum.model.request.TransferCustomErc20;
import io.tatum.model.request.TransferEthErc20;
import io.tatum.utils.ApiKey;
import io.tatum.utils.Async;
import io.tatum.utils.NumericUtil;
import io.tatum.utils.ObjectValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.*;
import static io.tatum.model.request.Currency.ETH;
import static org.web3j.utils.Convert.Unit.ETHER;
import static org.web3j.utils.Convert.Unit.GWEI;

@Log4j2
public class EthTx {

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

                var url = StringUtils.isNotEmpty(provider) ? provider : TATUM_API_URL + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                Web3j web3j = Web3j.build(new HttpService(url));
                Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
                String clientVersion = web3ClientVersion.getWeb3ClientVersion();
                log.info(clientVersion);

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

                String data = null;
                if (StringUtils.isNotEmpty(_data)) {
                    if (Numeric.containsHexPrefix(_data)) {
                        data = _data;
                    } else if (NumericUtil.isHexadecimal(_data)) {
                        data = Numeric.prependHexPrefix(_data);
                    } else {
                        data = Numeric.toHexString(_data.getBytes());
                    }
                }

                RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit,
                        _to, BigInteger.ZERO, data);

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

                var url = StringUtils.isNotEmpty(provider) ? provider : TATUM_API_URL + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                Web3j web3j = Web3j.build(new HttpService(url));
                Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
                String clientVersion = web3ClientVersion.getWeb3ClientVersion();
                log.info(clientVersion);

                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                BigInteger gasLimit = null;
                BigInteger gasPrice;
                if (_fee != null) {
                    gasPrice = new BigInteger(_fee.getGasPrice());
                } else {
                    gasPrice = Convert.fromWei(ethGetGasPriceInWei(), GWEI).toBigInteger();
                }

                String data = null;
                if (StringUtils.isNotEmpty(_data)) {
                    if (Numeric.containsHexPrefix(_data)) {
                        data = _data;
                    } else if (NumericUtil.isHexadecimal(_data)) {
                        data = Numeric.prependHexPrefix(_data);
                    } else {
                        data = Numeric.toHexString(_data.getBytes());
                    }
                }

                Transaction prepareTx;

                BigInteger amount;

                if (_currency == ETH) {
                    amount = Convert.toWei(_amount, ETHER).toBigInteger();
                    prepareTx = new Transaction(StringUtils.EMPTY, _nonce, gasPrice, gasLimit, _to, amount, data);

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
                    prepareTx = new Transaction(StringUtils.EMPTY, _nonce, gasPrice, gasLimit, contractAddress, amount, txData);
                }

                if (_fee != null) {
                    gasLimit = new BigInteger(_fee.getGasLimit());
                } else {
                    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(prepareTx).send();
                    gasLimit = ethEstimateGas.getAmountUsed().add(BigInteger.valueOf(5000));
                }

                RawTransaction rawTransaction = RawTransaction.createTransaction(_nonce, gasPrice, gasLimit,
                        prepareTx.getTo(), amount, prepareTx.getData());

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

                var url = StringUtils.isNotEmpty(provider) ? provider : TATUM_API_URL + "/v3/ethereum/web3/" + ApiKey.getInstance().getApiKey();
                Web3j web3j = Web3j.build(new HttpService(url));
                Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().sendAsync().get();
                String clientVersion = web3ClientVersion.getWeb3ClientVersion();
                log.info(clientVersion);

                Credentials credentials = Credentials.create(body.getFromPrivateKey());

                BigInteger gasLimit = null;
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
                Transaction prepareTx = new Transaction(StringUtils.EMPTY, _nonce, gasPrice, gasLimit, _contractAddress, amount, txData);

                if (_fee != null) {
                    gasLimit = new BigInteger(_fee.getGasLimit());
                } else {
                    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(prepareTx).send();
                    gasLimit = ethEstimateGas.getAmountUsed().add(BigInteger.valueOf(5000));
                }

                RawTransaction rawTransaction = RawTransaction.createTransaction(_nonce, gasPrice, gasLimit,
                        prepareTx.getTo(), amount, prepareTx.getData());

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
}
