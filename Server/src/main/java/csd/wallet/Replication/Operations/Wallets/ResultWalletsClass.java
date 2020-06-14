package csd.wallet.Replication.Operations.Wallets;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;

import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Wallet;
import csd.wallet.Services.Wallets.ServiceWalletsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ErrorCode.*;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultWalletsClass implements ResultWalletsInterface {

	@Autowired
	ServiceWalletsClass wallets;

	@Override
	public Result createWallet(long accId, Wallet wallet) {
		try {
			return ok(wallets.createWallet(accId, wallet));
		} catch (EmptyWalletNameException e) {
			return getError(BAD_REQUEST);
		} catch (AuthenticationErrorException e) {
			return getError(UNAUTHORIZED);
		}
	}

	@Override
	public Result deleteWallet(long accId, long id) {
		try {
			wallets.deleteWallet(id, id);
			return ok();
		} catch (WalletNotExistsException e) {
			return getError(NOT_FOUND);
		} catch (AuthenticationErrorException e) {
			return getError(UNAUTHORIZED);
		}
	}

	@Override
	public Result getCurrentAmount(long accId, long id) {
		try {
			return (ok(wallets.getCurrentAmount(id, id)));
		} catch (WalletNotExistsException e) {
			return (getError(NOT_FOUND));
		} catch (AuthenticationErrorException e) {
			return getError(UNAUTHORIZED);
		}
	}

	@Override
	public Result getWalletInfo(long accId, long id) {
		try {
			return (ok(wallets.getWalletInfo(id, id)));
		} catch (WalletNotExistsException e) {
			return (getError(NOT_FOUND));
		} catch (AuthenticationErrorException e) {
			return getError(UNAUTHORIZED);
		}
	}
}
