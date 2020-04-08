package csd.wallet.Controllers.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Replication.InvokesTypes;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
import csd.wallet.Utils.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import bftsmart.tom.ServiceProxy;

import java.io.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerTransfersClass implements ControllerTransfersInterface, Serializable {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Void> addMoney(AddRemoveForm idAmount) {
        Logger.info("Request: ADDMONEY");
        try {
            return response(bftClient.getInvoke(RequestType.TRANSFERS_ADD, InvokesTypes.ORDERED, idAmount));
        } catch (Exception e) {
            Logger.error("ADDMONEY");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> removeMoney(AddRemoveForm idAmount) {
        Logger.info("Request: REMOVEMONEY");
        try {
            return response(bftClient.getInvoke(RequestType.TRANSFERS_REMOVE, InvokesTypes.ORDERED, idAmount));
        } catch (Exception e) {
            Logger.error("REMOVEMONEY");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> transfer(Transfer transfer) {
        Logger.info("Request: TRANSFER");
        try {
            return response(bftClient.getInvoke(RequestType.TRANSFERS_TRANSFER, InvokesTypes.ORDERED, transfer));
        } catch (Exception e) {
            Logger.error("TRANSFER");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ListWrapper> ledgerOfGlobalTransfers() {
        Logger.info("Request: LEDGEROFGLOBALTRANSFERS");
        try {
            ListWrapper result = bftClient.getInvoke(RequestType.TRANSFERS_GLOBALTRANSFERS, InvokesTypes.UNORDERED);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Logger.error("LEDGEROFGLOBALTRANSFERS");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ListWrapper> ledgerOfWalletTransfers(long id) {
        Logger.info("Request: LEDGEROFWALLETTRANSFERS");
        try {
            ListWrapper result = bftClient.getInvoke(RequestType.TRANSFERS_WALLETTRANSFERS, InvokesTypes.UNORDERED, id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Logger.error("LEDGEROFWALLETTRANSFERS");
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
