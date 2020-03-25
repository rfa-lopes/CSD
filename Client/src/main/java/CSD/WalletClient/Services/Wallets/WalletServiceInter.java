package CSD.WalletClient.Services.Wallets;

import CSD.WalletClient.Models.Wallet;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

public interface WalletServiceInter {

    ResponseEntity<Long> create(String name) throws URISyntaxException;

    void delete(long id) throws URISyntaxException ;

    ResponseEntity<Long> getAmount(long id) throws URISyntaxException;

    ResponseEntity<Wallet> getInfo(long id) throws URISyntaxException;
}
