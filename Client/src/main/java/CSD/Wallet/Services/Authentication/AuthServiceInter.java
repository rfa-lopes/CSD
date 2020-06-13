package CSD.Wallet.Services.Authentication;

import CSD.Wallet.Models.SignedResults;
import org.springframework.http.ResponseEntity;

public interface AuthServiceInter {

    ResponseEntity<SignedResults> login(String username, String password);

    ResponseEntity<SignedResults> create(String username, String password);
}
