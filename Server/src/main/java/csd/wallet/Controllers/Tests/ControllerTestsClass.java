package csd.wallet.Controllers.Tests;

import csd.wallet.Services.Tests.ServiceTestsClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerTestsClass implements ControllerTestsInterface {

    @Autowired
    ServiceTestsClass tests;

    @Override
    public ResponseEntity<String> test1() {
        String serviceResponse = tests.test1();
        //TODO: Replicate
        return ResponseEntity.ok(serviceResponse);
    }

    @Override
    public ResponseEntity<String> test2() {
        String serviceResponse = tests.test2();
        //TODO: Replicate
        return ResponseEntity.ok(serviceResponse);
    }
}
