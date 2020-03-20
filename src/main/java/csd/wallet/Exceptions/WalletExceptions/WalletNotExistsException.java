package csd.wallet.Exceptions.WalletExceptions;

import csd.wallet.Utils.Log;

public class WalletNotExistsException extends Exception{

    private static String MESSAGE = "Wallet ID does not exists: %d";

    private Log log = Log.getInstance(WalletNotExistsException.class);

    public WalletNotExistsException(long id) {
        super();
        log.warn(MESSAGE, id);
        //log.error(MESSAGE, id);
        //log.info(MESSAGE, id);
    }

}
