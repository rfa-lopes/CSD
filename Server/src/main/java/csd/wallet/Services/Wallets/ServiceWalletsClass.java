package csd.wallet.Services.Wallets;

import csd.wallet.WebFilters.AuthenticatorFilter;
import org.springframework.beans.factory.annotation.Autowired;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AccountWalletsAssociation;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.AccountWalletsAssociationRepository;
import csd.wallet.Repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class ServiceWalletsClass implements ServiceWalletsInterface {

    @Autowired
    private WalletRepository wallets;

    @Autowired
    private AccountWalletsAssociationRepository accountWalletsAssociation;

    @Override
    public long createWallet(long accId, Wallet wallet) throws EmptyWalletNameException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        if (wallet.getName().equals(""))
            throw new EmptyWalletNameException();

        //Amount com um valor
        Wallet w = wallets.save(wallet);
        accountWalletsAssociation.save(new AccountWalletsAssociation(accId, w.getId()));
        return w.getId();
    }

    @Override
    public void deleteWallet(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        //So pode apagar as suas wallets
        if (!isWalletOwner(accId, id))
            throw new AuthenticationErrorException();

        Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
        wallets.delete(w);
        //accountWalletsAssociation.deleteAllByWalletId(id);
    }

    @Override
    public BigInteger getCurrentAmount(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        //So pode ver o amount das suas wallets
        if (!isWalletOwner(accId, id))
            throw new AuthenticationErrorException();

        Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
        return new BigInteger(w.getAmount_add());
    }

    @Override
    public Wallet getWalletInfo(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        //So pode ver a informacao das suas wallets
        if (!isWalletOwner(accId, id))
            throw new AuthenticationErrorException();

        return wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
    }

    private boolean isWalletOwner(long accId, long walletId) {
        List<AccountWalletsAssociation> accWallets = accountWalletsAssociation.findAllByUserId(accId);
        for (AccountWalletsAssociation a : accWallets)
            if (a.getWalletId() == walletId)
                return true;
        return false;
    }

}
