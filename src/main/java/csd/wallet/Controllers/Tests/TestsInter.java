package csd.wallet.Controllers.Tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tests")
public interface TestsInter {

    /**
     * @return TEST1
     */
    @GetMapping(value = "/helloworld")
    ResponseEntity<String> test1();

    /**
     * @return TEST2
     */
    @PostMapping(value = "/helloworld")
    ResponseEntity<String> test2();

}
