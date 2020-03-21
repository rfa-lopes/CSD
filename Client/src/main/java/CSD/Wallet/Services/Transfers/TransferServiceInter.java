package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.TransferModel1;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransferServiceInter {

    ResponseEntity<Void> transfer (long fromId, long toId, long amount);

    ResponseEntity<Void> addAmount (long id, long amount);

    ResponseEntity<List<TransferModel1>> listGlobalTransfers ();

    ResponseEntity<List<TransferModel1>> listWalletTransfers (long id);

}
