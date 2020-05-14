package csd.wallet.Controllers.Wallets;

import bftsmart.tom.core.messages.TOMMessageType;
import csd.wallet.Models.Wallet;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Replication.MessageType;
import csd.wallet.Utils.Logger;
import csd.wallet.Enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class ControllerWalletsClass extends RestResource implements ControllerWalletsInterface, Serializable {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Long> createWallet(Wallet wallet) {
        Logger.info("Request: CREATEWALLET");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_CREATE, MessageType.ASYNC_REQUEST, wallet));
    }

    @Override
    public ResponseEntity<Void> deleteWallet(long id) {
        Logger.info("Request: DELETEWALLET");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_DELETE, MessageType.ASYNC_REQUEST, id));
    }

    @Override
    public ResponseEntity<Long> getCurrentAmount(long id) {
        Logger.info("Request: GETCURRENTAMOUNT");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_AMOUNT, MessageType.ASYNC_REQUEST, id));
    }

    @Override
    public ResponseEntity<Wallet> getWalletInfo(long id) {
        Logger.info("Request: GETWALLETINFO");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_INFO, MessageType.ASYNC_REQUEST, id));
    }
}