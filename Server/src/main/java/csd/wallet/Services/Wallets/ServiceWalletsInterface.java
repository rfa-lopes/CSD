package csd.wallet.Services.Wallets;

import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.Wallet;
import org.springframework.stereotype.Service;

@Service
public interface ServiceWalletsInterface {

    long createWallet (Wallet wallet) throws EmptyWalletNameException;

    void deleteWallet (long id) throws WalletNotExistsException;

    long getCurrentAmount (long id) throws WalletNotExistsException;

    Wallet getWalletInfo (long id) throws WalletNotExistsException;

}
