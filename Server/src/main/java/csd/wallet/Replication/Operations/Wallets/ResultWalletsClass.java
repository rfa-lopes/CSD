package csd.wallet.Replication.Operations.Wallets;

import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletAlreadyExistException;
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
    public Result createWallet(Wallet wallet) {
        try {
            return ok(wallets.createWallet(wallet));
        } catch (EmptyWalletNameException e) {
            return getError(BAD_REQUEST);
        }
    }

    @Override
    public Result deleteWallet(long id) {
        try {
            wallets.deleteWallet(id);
            return ok();
        } catch (WalletNotExistsException e) {
            return getError(NOT_FOUND);
        }
    }

    @Override
    public Result getCurrentAmount(long id) {
        try {
            return (ok(wallets.getCurrentAmount(id)));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        }
    }

    @Override
    public Result getWalletInfo(long id) {
        try {
            return (ok(wallets.getWalletInfo(id)));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        }
    }
}
