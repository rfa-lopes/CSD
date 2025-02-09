package csd.wallet.Services.Wallets;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletAlreadyExistException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.Wallet;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public interface ServiceWalletsInterface {

	long createWallet(long accId, Wallet wallet)
			throws EmptyWalletNameException, WalletAlreadyExistException, AuthenticationErrorException;

	void deleteWallet(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException;

	BigInteger getCurrentAmount(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException;

	Wallet getWalletInfo(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException;
}
