package csd.wallet.Controllers.Tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/test")
public interface TestsInter {

    /**
     * @return TEST1
     */
    @GetMapping(value = "/1")
    ResponseEntity<String> test1();

    /**
     * @return TEST2
     */
    @GetMapping(value = "/2")
    ResponseEntity<String> test2();

}
