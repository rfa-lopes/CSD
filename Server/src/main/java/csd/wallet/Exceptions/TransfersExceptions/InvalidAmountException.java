package csd.wallet.Exceptions.TransfersExceptions;

import csd.wallet.Controllers.Transfers.TransfersClass;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Utils.Log;

public class InvalidAmountException extends Exception{

    private static String MESSAGE1 = "Invalid Amount: [" + TransfersClass.MIN_AMOUNT + ", " + TransfersClass.MAX_AMOUNT + "]";
    private static String MESSAGE2 = "Wallet without that amount to remove. Available amount: %d";

    private Log log = Log.getInstance(WalletNotExistsException.class);

    public InvalidAmountException() {
        super();
        log.warn(MESSAGE1);
    }

    public InvalidAmountException(long amount) {
        super();
        log.warn(MESSAGE2, amount);
    }

}
