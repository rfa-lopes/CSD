package csd.wallet.Services.Wallets;

import org.springframework.beans.factory.annotation.Autowired;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AccountWalletsAssociation;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.AccountWalletsAssociationRepository;
import csd.wallet.Repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceWalletsClass implements ServiceWalletsInterface {

	@Autowired
	private WalletRepository wallets;

	@Autowired
	private AccountWalletsAssociationRepository accWallets;

	@Override
	public long createWallet(long accId, Wallet wallet) throws EmptyWalletNameException {

		accWallets.save(new AccountWalletsAssociation(accId, wallet.getId()));

		if (wallet.getName() == null)
			throw new EmptyWalletNameException();

		Wallet w = wallets.save(wallet);
		return w.getId();
	}

	@Override
	public void deleteWallet(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

		if (accWallets.findByUserIdAndWalletId(accId, id).isEmpty())
			throw new AuthenticationErrorException();

		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		wallets.delete(w);
	}

	@Override
	public long getCurrentAmount(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

		if (accWallets.findByUserIdAndWalletId(accId, id).isEmpty())
			throw new AuthenticationErrorException();

		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		return w.getAmount();
	}

	@Override
	public Wallet getWalletInfo(long accId, long id) throws WalletNotExistsException, AuthenticationErrorException {

		if (accWallets.findByUserIdAndWalletId(accId, id).isEmpty())
			throw new AuthenticationErrorException();

		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		return w;
	}
}
