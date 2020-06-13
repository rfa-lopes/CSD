package csd.wallet.Exceptions.AccountsExceptions;

import csd.wallet.Utils.Logger;

public class AccountUsernameAlreadyExistsException extends Exception{

    private static String MESSAGE = "Account Already exist";

    public AccountUsernameAlreadyExistsException() {
        super();
        Logger.warn(MESSAGE);
    }

}