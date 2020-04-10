package csd.wallet.Controllers.Wallets;

import csd.wallet.Models.Wallet;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Replication.InvokesTypes;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
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
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_CREATE, InvokesTypes.ORDERED, wallet));
    }

    @Override
    public ResponseEntity<Void> deleteWallet(long id) {
        Logger.info("Request: DELETEWALLET");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_DELETE, InvokesTypes.ORDERED, id));
    }

    @Override
    public ResponseEntity<Long> getCurrentAmount(long id) {
        Logger.info("Request: GETCURRENTAMOUNT");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_AMOUNT, InvokesTypes.UNORDERED, id));
    }

    @Override
    public ResponseEntity<Wallet> getWalletInfo(long id) {
        Logger.info("Request: GETWALLETINFO");
        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_INFO, InvokesTypes.UNORDERED, id));
    }
}