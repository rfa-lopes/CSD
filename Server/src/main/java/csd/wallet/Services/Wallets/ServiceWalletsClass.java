package csd.wallet.Services.Wallets;

import org.springframework.beans.factory.annotation.Autowired;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.WalletRepository;
import csd.wallet.Utils.Log;
import org.springframework.stereotype.Service;

@Service
public class ServiceWalletsClass implements ServiceWalletsInterface {

	@Autowired
	private WalletRepository wallets;

	private Log log = Log.getInstance(ServiceWalletsClass.class);

	@Override
	public long createWallet(Wallet wallet) throws EmptyWalletNameException {
		if (wallet.getName().equals(null))
			throw new EmptyWalletNameException();
		Wallet w = wallets.save(new Wallet(wallet.getName()));
		log.info("Create Wallet for: %s", wallet.getName());
		return w.getId();
	}

	@Override
	public void deleteWallet(long id) throws WalletNotExistsException {
		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		wallets.delete(w);
		log.info("Delete Wallet : %d", id);
	}

	@Override
	public long getCurrentAmount(long id) throws WalletNotExistsException {
		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		log.info("Get Wallet Amount for id : %d", id);
		return w.getAmount();
	}

	@Override
	public Wallet getWalletInfo(long id) throws WalletNotExistsException {
		Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));
		log.info("Get Wallet info for id : %d", id);
		return w;
	}
}
