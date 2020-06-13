package csd.wallet.Controllers.Login;

import csd.wallet.Models.Account;
import csd.wallet.Models.Wallet;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Replication.MessageType;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Utils.Logger;
import csd.wallet.Enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class ControllerLoginClass extends RestResource implements ControllerLoginInterface, Serializable {

    @Autowired
    BFTClient bftClient;

	@Override
	public ResponseEntity<Result> login(Account account) {
      Logger.info("Request: LOGIN");
      return super.getResponse(bftClient.getInvoke(RequestType.LOGIN, MessageType.ASYNC_REQUEST, account));
	}

//    @Override
//    public ResponseEntity<Result> createWallet(Wallet wallet) {
//        Logger.info("Request: CREATEWALLET");
//        return super.getResponse(bftClient.getInvoke(RequestType.WALLET_CREATE, MessageType.ASYNC_REQUEST, wallet));
//    }


}