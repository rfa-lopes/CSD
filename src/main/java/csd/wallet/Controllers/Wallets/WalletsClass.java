package csd.wallet.Controllers.Wallets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import csd.wallet.Models.Wallet;
import csd.wallet.Repository.TransferRepository;
import csd.wallet.Repository.WalletRepository;

import org.springframework.http.ResponseEntity;

@RestController
public class WalletsClass implements WalletsInter{
	
	@Autowired
	private TransferRepository transfers;
	@Autowired
	private WalletRepository wallets;
	
    @Override
    public ResponseEntity<Void> createWallet(String name) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteWallet(long id) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Integer> getCurrentAmount(long id) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Wallet> getWalletInfo(long id) {
        return ResponseEntity.noContent().build();
    }
}
