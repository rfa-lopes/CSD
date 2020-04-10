package csd.wallet.Controllers.Tests;

import csd.wallet.Replication.BFTClient;
import csd.wallet.Replication.InvokesTypes;
import csd.wallet.Controllers.RestResource;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
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
        return super.getResponse(bftClient.getInvoke(RequestType.TEST_1, InvokesTypes.ORDERED));
    }

    @Override
    public ResponseEntity<String> test2() {
        Logger.info("Request: TEST2");
        return super.getResponse(bftClient.getInvoke(RequestType.TEST_2, InvokesTypes.UNORDERED));
    }
}
