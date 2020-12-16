package io.tatum.ledger;

import io.tatum.model.request.BlockAmount;
import io.tatum.model.request.CreateAccount;
import io.tatum.model.request.CreateAccountsBatch;
import io.tatum.model.response.ledger.Account;
import io.tatum.model.response.ledger.AccountBalance;
import io.tatum.model.response.ledger.Blockage;
import io.tatum.utils.Async;
import io.tatum.utils.BaseUrl;
import io.tatum.utils.ObjectValidator;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static io.tatum.constants.Constant.EMPTY_BODY;

@Log4j2
public class LedgerAccount {

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getAccountByAccountId" target="_blank">Tatum API documentation</a>
     */
    public Account getAccountById(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/" + id;
        return Async.get(uri, Account.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/createAccount" target="_blank">Tatum API documentation</a>
     */
    public Account createAccount(CreateAccount account) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(account)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account";
        return Async.post(uri, account, Account.class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/createAccountBatch" target="_blank">Tatum API documentation</a>
     */
    public Account[] createAccounts(CreateAccountsBatch accounts) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(accounts)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/batch";
        return Async.post(uri, accounts, Account[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getBlockAmount" target="_blank">Tatum API documentation</a>
     */
    public Blockage[] getBlockedAmountsByAccountId(String id, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/block/" + id + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, Blockage[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/blockAmount" target="_blank">Tatum API documentation</a>
     */
    public String blockAmount(String id, BlockAmount block) throws IOException, ExecutionException, InterruptedException {
        if (!ObjectValidator.isValidated(block)) {
            return null;
        }
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/block/" + id;
        var res = Async.post(uri, block);
        if (res != null) {
            JSONObject jsonObject = new JSONObject(res);
            return jsonObject.getString("id");
        }
        return null;
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteBlockAmount" target="_blank">Tatum API documentation</a>
     */
    public void deleteBlockedAmount(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/block/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deleteAllBlockAmount" target="_blank">Tatum API documentation</a>
     */
    public void deleteBlockedAmountForAccount(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/block/account/" + id;
        Async.delete(uri);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/activateAccount" target="_blank">Tatum API documentation</a>
     */
    public void activateAccount(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/" + id + "/activate";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/deactivateAccount" target="_blank">Tatum API documentation</a>
     */
    public void deactivateAccount(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/" + id + "/deactivate";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/freezeAccount" target="_blank">Tatum API documentation</a>
     */
    public void freezeAccount(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/" + id + "/freeze";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/unfreezeAccount" target="_blank">Tatum API documentation</a>
     */
    public void unfreezeAccount(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/" + id + "/unfreeze";
        Async.put(uri, EMPTY_BODY);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getAccountsByCustomerId" target="_blank">Tatum API documentation</a>
     */
    public Account[] getAccountsByCustomerId(String id, Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/customer/" + id + "?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, Account[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getAllAccounts" target="_blank">Tatum API documentation</a>
     */
    public Account[] getAllAccounts(Integer pageSize, Integer offset) throws ExecutionException, InterruptedException {
        Integer _pageSize = (pageSize == null || pageSize < 0 || pageSize > 50) ? 50 : pageSize;
        Integer _offset = (offset == null || offset < 0) ? 0 : offset;
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account?pageSize=" + _pageSize + "&offset=" + _offset;
        return Async.get(uri, Account[].class);
    }

    /**
     * For more details, see <a href="https://tatum.io/apidoc#operation/getAccountBalance" target="_blank">Tatum API documentation</a>
     */
    public AccountBalance getAccountBalance(String id) throws ExecutionException, InterruptedException {
        String uri = BaseUrl.getInstance().getUrl() + "/v3/ledger/account/" + id + "/balance";
        return Async.get(uri, AccountBalance.class);
    }

}
