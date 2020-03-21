package CSD.Wallet.Services.Wallets;

import CSD.Wallet.Models.WalletModel1;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

public interface WalletServiceInter {

    ResponseEntity<Void> create(String name) throws URISyntaxException;

    void delete(long id) throws URISyntaxException ;

    ResponseEntity<Long> getAmount(long id) throws URISyntaxException;

    ResponseEntity<String> getInfo(long id) throws URISyntaxException;
}
