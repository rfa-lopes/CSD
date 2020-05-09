package csd.wallet.Controllers.Tests;

import bftsmart.tom.core.messages.TOMMessageType;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Utils.Logger;
import csd.wallet.Enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTestsClass extends RestResource implements ControllerTestsInterface {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<String> test1() {
        Logger.info("Request: TEST1");
        return super.getResponse(bftClient.getInvoke(RequestType.TEST_1, TOMMessageType.ORDERED_REQUEST));
    }

    @Override
    public ResponseEntity<String> test2() {
        Logger.info("Request: TEST2");
        return super.getResponse(bftClient.getInvoke(RequestType.TEST_2, TOMMessageType.UNORDERED_REQUEST));
    }

    @Override
    public ResponseEntity<String> test3() {
        Logger.info("Request: TEST3");
        return super.getResponse(bftClient.getInvoke(RequestType.TEST_3, TOMMessageType.UNORDERED_REQUEST));
        //return super.getResponse(bftClient.getInvoke(RequestType.TEST_3, TOMMessageType.UNORDERED_HASHED_REQUEST));
    }

    @Override
    public ResponseEntity<Void> test4() {
        Logger.info("Request: TEST4");
        return super.getResponse(bftClient.getInvoke(RequestType.TEST_4, TOMMessageType.ORDERED_REQUEST));
        //return super.getResponse(bftClient.getInvoke(true, RequestType.TEST_4, TOMMessageType.ORDERED_REQUEST));
    }
}
