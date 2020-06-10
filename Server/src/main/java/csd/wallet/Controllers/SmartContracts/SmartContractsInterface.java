package csd.wallet.Controllers.SmartContracts;


import csd.wallet.Models.SmartContract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.security.SignedObject;

@RestController
@RequestMapping(value = "/smartcontract")
public interface SmartContractsInterface {

    /**
     * @return TEST1
     */
    @PostMapping(value = "/execute")
    ResponseEntity<Void> executeSmartContract(@RequestBody SignedObject smartContract);

}
