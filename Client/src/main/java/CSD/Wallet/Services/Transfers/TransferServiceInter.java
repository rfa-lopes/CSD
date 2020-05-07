package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.Transfer;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public interface TransferServiceInter {

    ResponseEntity<Void> transfer (long fromId, long toId, long amount);

    ResponseEntity<Void> addAmount (long id, long amount);

    ResponseEntity<Void> removeAmount (long id, long amount);

    ResponseEntity<List<Transfer>> listGlobalTransfers () throws URISyntaxException;

    ResponseEntity<List<Transfer>> listWalletTransfers (long id) throws URISyntaxException;

}
