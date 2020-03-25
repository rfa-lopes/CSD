package csd.wallet.Controllers.Wallets;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.WalletRepository;
import csd.wallet.Utils.Log;

@RestController
public class WalletsClass implements WalletsInter {

	@Autowired
	private WalletRepository wallets;

	private Log log = Log.getInstance(WalletsClass.class);

	@Override
	public ResponseEntity<Long> createWallet(Wallet wallet) {
		try {
			if (wallet.getName().equals(null))
				throw new EmptyWalletNameException();

			Wallet w = wallets.save(new Wallet(wallet.getName()));

			log.info("Create Wallet for: %s", wallet.getName());

			return ResponseEntity.ok(w.getId());

		} catch (EmptyWalletNameException e) {
			return ResponseEntity.badRequest().build();
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<Void> deleteWallet(long id) {

		try {

			Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));

			wallets.delete(w);

			log.info("Delete Wallet : %d", id);

			return ResponseEntity.noContent().build();

		} catch (WalletNotExistsException e) {
			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<Long> getCurrentAmount(long id) {
		try {
			Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));

			log.info("Get Wallet Amount for id : %d", id);

			return ResponseEntity.ok(w.getAmount());

		} catch (WalletNotExistsException e) {
			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();

		}
	}

	@Override
	public ResponseEntity<Wallet> getWalletInfo(long id) {
		try {
			Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));

			log.info("Get Wallet info for id : %d", id);

			return ResponseEntity.ok(w);

		} catch (WalletNotExistsException e) {
			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();

		}
	}
}
