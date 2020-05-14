package csd.wallet.Controllers.Transfers;

import bftsmart.tom.core.messages.TOMMessageType;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Replication.MessageType;
import csd.wallet.Utils.Logger;
import csd.wallet.Enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

@RestController
public class ControllerTransfersClass extends RestResource implements ControllerTransfersInterface, Serializable {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Void> addMoney(AddRemoveForm idAmount) {
        Logger.info("Request: ADDMONEY");
        return super.getResponse(bftClient.getInvoke(RequestType.TRANSFERS_ADD, MessageType.ASYNC_REQUEST, idAmount));
    }

    @Override
    public ResponseEntity<Void> removeMoney(AddRemoveForm idAmount) {
        Logger.info("Request: REMOVEMONEY");
        return super.getResponse(bftClient.getInvoke(RequestType.TRANSFERS_REMOVE, MessageType.ASYNC_REQUEST, idAmount));
    }

    @Override
    public ResponseEntity<Void> transfer(Transfer transfer) {
        Logger.info("Request: TRANSFER");
        return super.getResponse(bftClient.getInvoke(RequestType.TRANSFERS_TRANSFER, MessageType.ASYNC_REQUEST, transfer));
    }

    @Override
    public ResponseEntity<List<Transfer>> ledgerOfGlobalTransfers() {
        Logger.info("Request: LEDGEROFGLOBALTRANSFERS");
        return super.getResponse(bftClient.getInvoke(RequestType.TRANSFERS_GLOBALTRANSFERS, MessageType.ASYNC_REQUEST));
    }

    @Override
    public ResponseEntity<List<Transfer>> ledgerOfWalletTransfers(long id) {
        Logger.info("Request: LEDGEROFWALLETTRANSFERS");
        return super.getResponse(bftClient.getInvoke(RequestType.TRANSFERS_WALLETTRANSFERS, MessageType.ASYNC_REQUEST, id));
    }

}
