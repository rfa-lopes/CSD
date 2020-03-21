package CSD.Wallet.Services.Transfers;

import org.springframework.http.ResponseEntity;

public interface TransferServiceInter {

    ResponseEntity<Void> transfer (long fromId, long toId, long amount);

}
