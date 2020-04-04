package csd.wallet.Exceptions.WalletExceptions;

import csd.wallet.Utils.Logger;

public class WalletNotExistsException extends Exception {

    private static String MESSAGE = "Wallet ID does not exists: %d";

    public WalletNotExistsException(long id) {
        super();
        Logger.warn(MESSAGE, id);
    }

}
