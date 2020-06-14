package csd.wallet.Controllers.SmartContracts;

import csd.wallet.Controllers.RestResource;

import csd.wallet.Enums.RequestType;
import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.BFTClient;

import csd.wallet.Replication.MessageType;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartContractsClass extends RestResource implements SmartContractsInterface {

    @Autowired
    BFTClient bftClient;

    @SuppressWarnings("all")
    @Override
    public ResponseEntity<Result> executeSmartContract(long accId, SmartContract smartContract) {
        Logger.info("Request: SMART CONTRACT EXECUTE");
        return super.getResponse(bftClient.getInvoke(RequestType.SMART_CONTRACT_EXECUTE,
                MessageType.ASYNC_REQUEST, accId, smartContract));
    }
}
