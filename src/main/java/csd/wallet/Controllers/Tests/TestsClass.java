package csd.wallet.Controllers.Tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestsClass implements TestsInter {

    @Override
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok("Test1");
    }

    @Override
    public ResponseEntity<String> test2() {
        return ResponseEntity.status(301).body("test2");
    }
}
