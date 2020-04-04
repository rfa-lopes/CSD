package csd.wallet.Exceptions.TransfersExceptions;

import csd.wallet.Services.Transfers.ServiceTransfersClass;
import csd.wallet.Utils.Logger;

public class InvalidAmountException extends Exception{

    private static String MESSAGE1 = "Invalid Amount: [" + ServiceTransfersClass.MIN_AMOUNT + ", " + ServiceTransfersClass.MAX_AMOUNT + "]";
    private static String MESSAGE2 = "Wallet without that amount to remove. Available amount: %d";

    public InvalidAmountException() {
        super();
        Logger.warn(MESSAGE1);
    }

    public InvalidAmountException(long amount) {
        super();
        Logger.warn(MESSAGE2, amount+"");
    }

}
