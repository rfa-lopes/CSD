package csd.wallet.Controllers.SmartContracts;

import bftsmart.tom.core.messages.TOMMessageType;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Enums.RequestType;
import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmartContractsClass extends RestResource implements SmartContractsInterface {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Void> executeSmartContract(SmartContract smartContract) {
        Logger.info("Request: SMART CONTRACT EXECUTE");
        return super.getResponse(bftClient.getInvoke(RequestType.SMART_CONTRACT_EXECUTE, TOMMessageType.UNORDERED_REQUEST, smartContract));
    }
}
