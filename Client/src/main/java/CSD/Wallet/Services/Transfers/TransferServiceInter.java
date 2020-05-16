package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public interface TransferServiceInter {

    ResponseEntity<SignedResults> transfer (long fromId, long toId, long amount);

    ResponseEntity<SignedResults> addAmount (long id, long amount);

    ResponseEntity<SignedResults> removeAmount (long id, long amount);

    ResponseEntity<SignedResults> listGlobalTransfers () throws URISyntaxException;

    ResponseEntity<SignedResults> listWalletTransfers (long id) throws URISyntaxException;

}
