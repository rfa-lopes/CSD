package csd.wallet.Controllers.Tests;

import csd.wallet.Replication.BFTClient;
import csd.wallet.Replication.InvokesTypes;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerTestsClass implements ControllerTestsInterface {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<String> test1() {
        Logger.info("Request: TEST1");
        try {
            String result = bftClient.getInvoke(RequestType.TEST_1, InvokesTypes.ORDERED);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Logger.error("TEST1");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<String> test2() {
        Logger.info("Request: TEST2");
        try {
            String result = bftClient.getInvoke(RequestType.TEST_2, InvokesTypes.UNORDERED);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Logger.error("TEST2");
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }
}
