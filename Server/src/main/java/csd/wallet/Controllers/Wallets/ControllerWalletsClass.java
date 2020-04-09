package csd.wallet.Controllers.Wallets;


import csd.wallet.Models.ResponseWrapper;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Replication.InvokesTypes;
import csd.wallet.Models.Wallet;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
import csd.wallet.Utils.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerWalletsClass implements ControllerWalletsInterface,Serializable {
    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Long> createWallet(Wallet wallet) {
        Logger.info("Request: CREATEWALLET");
        try {
            ResponseWrapper responseWrapper = bftClient.getInvoke(RequestType.WALLET_CREATE, InvokesTypes.ORDERED, wallet);
            long id = (long) responseWrapper.isOk();
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            Logger.error("CREATEWALLET");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<Void> deleteWallet(long id) {
        Logger.info("Request: DELETEWALLET");
        try {
            ResponseWrapper response = bftClient.getInvoke(RequestType.WALLET_DELETE, InvokesTypes.ORDERED, id);
            if(response.getException() == null)
                return ResponseEntity.notFound().build();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            Logger.error("DELETEWALLET");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
    @Override
    public ResponseEntity<Long> getCurrentAmount(long id) {
        Logger.info("Request: GETCURRENTAMOUNT");
        try {
            ResponseWrapper responseWrapper = bftClient.getInvoke(RequestType.WALLET_AMOUNT, InvokesTypes.UNORDERED, id);
            long amount = (long) responseWrapper.isOk();
            return ResponseEntity.ok(amount);
        } catch (Exception e) {
            Logger.error("GETCURRENTAMOUNT");
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    public ResponseEntity<Wallet> getWalletInfo(long id) {
        Logger.info("Request: GETWALLETINFO");
        try {
            ResponseWrapper responseWrapper = bftClient.getInvoke(RequestType.WALLET_INFO, InvokesTypes.UNORDERED, id);
            Wallet response = (Wallet) responseWrapper.isOk();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Logger.error("GETWALLETINFO");
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<Void> response(ResponseType responseType) {
        switch (responseType) {
            case OK:
                return ResponseEntity.ok().build();
            case NOT_FOUND:
                return ResponseEntity.notFound().build();
            case BAD_REQUEST:
                return ResponseEntity.badRequest().build();
            default:
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}