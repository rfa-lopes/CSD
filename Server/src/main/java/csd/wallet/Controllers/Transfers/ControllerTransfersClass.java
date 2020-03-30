package csd.wallet.Controllers.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerTransfersClass implements ControllerTransfersInterface {

    @Autowired
    ServiceTransfersClass transfers;

    @Override
    public ResponseEntity<Void> addMoney(AddRemoveForm idAmount) {
        try {
            transfers.addMoney(idAmount);
            //TODO: Replicate
            return ResponseEntity.ok().build();
        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAmountException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> removeMoney(AddRemoveForm idAmount) {
        try {
            transfers.removeMoney(idAmount);
            //TODO: Replicate
            return ResponseEntity.ok().build();
        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAmountException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> transfer(Transfer transfer) {
        try {
            transfers.transfer(transfer);
            //TODO: Replicate
            return ResponseEntity.ok().build();
        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAmountException | TransferToSameWalletException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ListWrapper> ledgerOfGlobalTransfers() {
        try {
            ListWrapper serviceResponse = transfers.ledgerOfGlobalTransfers();
            //TODO: Replicate
            return ResponseEntity.ok(serviceResponse);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ListWrapper> ledgerOfWalletTransfers(long id) {
        try {
            ListWrapper serviceResponse = transfers.ledgerOfWalletTransfers(id);
            //TODO: Replicate
            return ResponseEntity.ok(serviceResponse);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
