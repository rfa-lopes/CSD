package CSD.Wallet.Services.Wallets;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Wallet;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface WalletServiceInter {

    ResponseEntity<SignedResults> create(String name) throws URISyntaxException;

    ResponseEntity<SignedResults> delete(long id) throws URISyntaxException;

    ResponseEntity<SignedResults> getAmount(long id) throws URISyntaxException;

    ResponseEntity<SignedResults> getInfo(long id) throws URISyntaxException;
}
