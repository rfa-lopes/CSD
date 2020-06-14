package csd.wallet.Controllers.Accounts;

import csd.wallet.Controllers.RestResource;
import csd.wallet.Enums.RequestType;
import csd.wallet.Models.Account;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Replication.MessageType;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerAccountsClass extends RestResource implements ControllerAccountsInterface{

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Result> createAccount(Account account) {
        Logger.info("Request: CREATE ACCOUNT");
        return super.getResponse(bftClient.getInvoke(RequestType.ACCOUNT_CREATE,
                MessageType.ASYNC_REQUEST, -2, account));
    }
}
