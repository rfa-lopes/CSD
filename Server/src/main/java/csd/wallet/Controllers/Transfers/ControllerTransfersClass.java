package csd.wallet.Controllers.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.StringWrapper;
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
import java.util.Base64;
import java.util.List;

@RestController
public class ControllerTransfersClass extends RestResource implements ControllerTransfersInterface, Serializable {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Void> addMoney(long accId, AddRemoveForm idAmount, String keys) {
        Logger.info("Request: ADDMONEY");
        bftClient.setKeys(keys);
        return super.getResponse(
                bftClient.getInvoke(RequestType.TRANSFERS_ADD, MessageType.ASYNC_REQUEST, accId, idAmount));
    }

    @Override
    public ResponseEntity<Void> removeMoney(long accId, AddRemoveForm idAmount, String keys) {
        Logger.info("Request: REMOVEMONEY");
        bftClient.setKeys(keys);
        return super.getResponse(
                bftClient.getInvoke(RequestType.TRANSFERS_REMOVE, MessageType.ASYNC_REQUEST, accId, idAmount));
    }

    @Override
    public ResponseEntity<Void> transfer(long accId, Transfer transfer, String keys) {
        Logger.info("Request: TRANSFER");
        bftClient.setKeys(keys);
        return super.getResponse(
                bftClient.getInvoke(RequestType.TRANSFERS_TRANSFER, MessageType.ASYNC_REQUEST, accId, transfer));
    }

    @Override
    public ResponseEntity<List<Transfer>> ledgerOfGlobalTransfers(long accId) {
        Logger.info("Request: LEDGEROFGLOBALTRANSFERS");
        return super.getResponse(
                bftClient.getInvoke(RequestType.TRANSFERS_GLOBALTRANSFERS, MessageType.ASYNC_REQUEST, accId));
    }

    @Override
    public ResponseEntity<List<Transfer>> ledgerOfWalletTransfers(long accId, long id) {
        Logger.info("Request: LEDGEROFWALLETTRANSFERS");
        return super.getResponse(
                bftClient.getInvoke(RequestType.TRANSFERS_WALLETTRANSFERS, MessageType.ASYNC_REQUEST, accId, id));
    }

    @Override
    public ResponseEntity<List<Transfer>> ledgerOfDateTransfers(long accId, StringWrapper date) {
        Logger.info("Request: LEDGEROFDATETRANSFERS");
        return super.getResponse(
                bftClient.getInvoke(RequestType.TRANSFERS_DATETRANSFERS, MessageType.ASYNC_REQUEST, accId, date.getValue()));
    }

}
