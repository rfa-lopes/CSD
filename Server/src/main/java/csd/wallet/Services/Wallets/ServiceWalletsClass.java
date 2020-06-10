package csd.wallet.Services.Wallets;

import org.springframework.beans.factory.annotation.Autowired;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceWalletsClass implements ServiceWalletsInterface {

	@Autowired
	private WalletRepository wallets;

	@Override
	public long createWallet(Wallet wallet) throws EmptyWalletNameException {
		if (wallet.getName() == null)
			throw new EmptyWalletNameException();
		Wallet newWallet = new Wallet(wallet.getName());
		Wallet w = wallets.save(newWallet);
		return w.getId();
	}

	@Override
	public void deleteWallet(long id) throws WalletNotExistsException {
		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		wallets.delete(w);
	}

	@Override
	public long getCurrentAmount(long id) throws WalletNotExistsException {
		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		return w.getAmount();
	}

	@Override
	public Wallet getWalletInfo(long id) throws WalletNotExistsException {
		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		return w;
	}
}
