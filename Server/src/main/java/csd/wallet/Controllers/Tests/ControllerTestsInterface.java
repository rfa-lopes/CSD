package csd.wallet.Controllers.Tests;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tests")
public interface ControllerTestsInterface {

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
