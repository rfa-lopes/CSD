package csd.wallet.Services.Accounts;

import csd.wallet.Exceptions.AccountsExceptions.AccountUsernameAlreadyExistsException;
import csd.wallet.Exceptions.AccountsExceptions.InvalidAccountUsernameException;
import csd.wallet.Models.Account;
import org.springframework.stereotype.Service;

@Service
public interface ServiceAccountsInterface {

    long create(Account account) throws AccountUsernameAlreadyExistsException, InvalidAccountUsernameException;

}
