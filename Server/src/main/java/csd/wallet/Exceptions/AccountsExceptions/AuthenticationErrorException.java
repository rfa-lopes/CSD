package csd.wallet.Exceptions.AccountsExceptions;

import csd.wallet.Utils.Logger;

public class AuthenticationErrorException extends Exception {

	private static String MESSAGE = "Authentication Error";

	public AuthenticationErrorException() {
		super();
		Logger.warn(MESSAGE);
	}
}
