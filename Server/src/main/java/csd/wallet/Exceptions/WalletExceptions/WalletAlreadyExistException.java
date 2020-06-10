package csd.wallet.Exceptions.WalletExceptions;

        import csd.wallet.Utils.Logger;

public class WalletAlreadyExistException extends Exception{

    private static String MESSAGE = "Wallet already exist.";

    public WalletAlreadyExistException() {
        super();
        Logger.warn(MESSAGE);
    }

}
