package csd.wallet.Controllers.Wallets;

import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Services.Wallets.ServiceWalletsClass;
import csd.wallet.Models.Wallet;
import csd.wallet.Utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerWalletsClass implements ControllerWalletsInterface {

    @Autowired
    ServiceWalletsClass wallets;

    private Log log = Log.getInstance(ControllerWalletsClass.class);

    @Override
    public ResponseEntity<Long> createWallet(Wallet wallet) {
        try {
            long serviceResponse = wallets.createWallet(wallet);
            //TODO: Replicate
            return ResponseEntity.ok(serviceResponse);
        } catch (EmptyWalletNameException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteWallet(long id) {
        try {
            wallets.deleteWallet(id);
            //TODO: Replicate
            return ResponseEntity.ok().build();
        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Long> getCurrentAmount(long id) {
        try {
            long serviceResponse = wallets.getCurrentAmount(id);
            //TODO: Replicate
            return ResponseEntity.ok(serviceResponse);
        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Wallet> getWalletInfo(long id) {
        try {
            Wallet serviceResponse = wallets.getWalletInfo(id);
            //TODO: Replicate
            return ResponseEntity.ok(serviceResponse);
        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
