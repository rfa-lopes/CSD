package csd.wallet.Replication.Wallets;

import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Replication.Result;
import csd.wallet.Models.Wallet;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import csd.wallet.Services.Wallets.ServiceWalletsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Result.ErrorCode.NOT_FOUND;
import static csd.wallet.Replication.Result.error;
import static csd.wallet.Replication.Result.ok;
import static csd.wallet.Replication.ServiceProxy.SignedResult.createSignedResult;

@Service
public class ResultWalletsClass implements ResultWalletsInterface{

    @Autowired
    ServiceWalletsClass wallets;

    @Override
    public SignedResult createWallet(Wallet wallet) {
        try {
             return createSignedResult(ok(new Long(wallets.createWallet(wallet))));
        } catch (EmptyWalletNameException e) {
            return createSignedResult(error(BAD_REQUEST));
        }
    }

    @Override
    public SignedResult deleteWallet(long id) {
        try {
            wallets.deleteWallet(id);
            return createSignedResult(ok());
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }

    @Override
    public SignedResult getCurrentAmount(long id) {
        try {
            return createSignedResult(ok(wallets.getCurrentAmount(id)));
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }

    @Override
    public SignedResult getWalletInfo(long id) {
        try {
            return createSignedResult(ok(wallets.getWalletInfo(id)));
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }
}
