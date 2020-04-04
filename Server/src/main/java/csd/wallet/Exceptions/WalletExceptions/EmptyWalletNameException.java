package csd.wallet.Exceptions.WalletExceptions;

import csd.wallet.Utils.Logger;

public class EmptyWalletNameException extends Exception{

    private static String MESSAGE = "Field: Name must not be empty";

    public EmptyWalletNameException() {
        super();
        Logger.warn(MESSAGE);
    }

}
