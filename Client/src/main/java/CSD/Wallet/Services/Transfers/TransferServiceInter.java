package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.ListWrapper;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface TransferServiceInter {

    ResponseEntity<Void> transfer (long fromId, long toId, long amount);

    ResponseEntity<Void> addAmount (long id, long amount);

    ResponseEntity<Void> removeAmount (long id, long amount);

    ResponseEntity<ListWrapper> listGlobalTransfers () throws URISyntaxException;

    ResponseEntity<ListWrapper> listWalletTransfers (long id) throws URISyntaxException;

}
