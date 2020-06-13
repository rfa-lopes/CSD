package csd.wallet.Exceptions.AccountsExceptions;

import csd.wallet.Utils.Logger;

public class InvalidAccountUsernameException extends Exception {

    private static String MESSAGE = "Invalid account username";

    public InvalidAccountUsernameException() {
        super();
        Logger.warn(MESSAGE);
    }

}
