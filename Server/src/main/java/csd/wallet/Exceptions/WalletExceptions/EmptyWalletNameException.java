package csd.wallet.Exceptions.WalletExceptions;

import csd.wallet.Utils.Log;

public class EmptyWalletNameException extends Exception{

    private static String MESSAGE = "Field: Name must not be empty";

    private Log log = Log.getInstance(EmptyWalletNameException.class);

    public EmptyWalletNameException() {
        super();
        log.warn(MESSAGE);
        //log.error(MESSAGE, id);
        //log.info(MESSAGE, id);
    }

}
