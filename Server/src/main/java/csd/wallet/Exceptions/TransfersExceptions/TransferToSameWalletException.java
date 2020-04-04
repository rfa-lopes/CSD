package csd.wallet.Exceptions.TransfersExceptions;

import csd.wallet.Utils.Logger;

public class TransferToSameWalletException extends Exception{

    private static String MESSAGE = "Transfer to same wallet: %d";

    public TransferToSameWalletException(long id) {
        super();
        Logger.warn(MESSAGE, id+"");
    }

}
