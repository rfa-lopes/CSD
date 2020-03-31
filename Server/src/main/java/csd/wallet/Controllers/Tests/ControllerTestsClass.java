package csd.wallet.Controllers.Tests;

import bftsmart.tom.ServiceProxy;
import csd.wallet.Services.Tests.ServiceTestsClass;

import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class ControllerTestsClass implements ControllerTestsInterface {

    @Autowired
    ServiceTestsClass tests;

    ServiceProxy serviceProxy;

    @Autowired
    ControllerTestsClass(@Value("${server.port}") int serverport){
        int clientId  = (serverport%100)/10;
        serviceProxy = new ServiceProxy(clientId);
    }

    @Override
    public ResponseEntity<String> test1() {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

            objOut.writeObject(RequestType.TEST_1);

            objOut.flush();
            byteOut.flush();
            byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
            if (reply.length == 0)
                return null;
            try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
                 ObjectInput objIn = new ObjectInputStream(byteIn)) {
                return ResponseEntity.ok((String)objIn.readObject());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exception putting value into map: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<String> test2() {
        String serviceResponse = tests.test2();
        //TODO: Replicate
        return ResponseEntity.ok(serviceResponse);
    }
}
