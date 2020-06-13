package csd.wallet.Replication.Operations.Accounts;

import csd.wallet.Exceptions.AccountsExceptions.AccountUsernameAlreadyExistsException;
import csd.wallet.Exceptions.AccountsExceptions.InvalidAccountUsernameException;
import csd.wallet.Models.Account;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Services.Accounts.ServiceAccountsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Operations.Result.ErrorCode.CONFLICT;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultAccountsClass implements ResultAccountsInterface {

    @Autowired
    ServiceAccountsClass serviceAccounts;

    @Override
    public Result createAccount(Account account) {

        try {
            return ok(serviceAccounts.create(account));
        } catch (AccountUsernameAlreadyExistsException e) {
            return (getError(CONFLICT));
        } catch (InvalidAccountUsernameException e) {
            return (getError(BAD_REQUEST));
        }
    }
}
