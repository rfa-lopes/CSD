package csd.wallet.Exceptions.TransfersExceptions;

import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Utils.Log;

public class TransferToSameWalletException extends Exception{

    private static String MESSAGE = "Transfer to same wallet: %d";

    private Log log = Log.getInstance(WalletNotExistsException.class);

    public TransferToSameWalletException(long id) {
        super();
        log.warn(MESSAGE, id);
    }

}
