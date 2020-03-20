package csd.wallet.Controllers.Transfers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import csd.wallet.Repository.TransferRepository;

import java.util.List;

@RestController
public class TransfersClass implements TransfersInter {
	
	@Autowired
	private TransferRepository transfers;
	
    @Override
    public ResponseEntity<Void> addMoney(long id, long amount) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeMoney(long id, long amount) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> transfer(long fromId, long toId, long amount) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Transfer>> ledgerOfGlobalTransfers() {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<Transfer>> LedgerOfWalletTransfers(long id) {
        return ResponseEntity.noContent().build();
    }
}
