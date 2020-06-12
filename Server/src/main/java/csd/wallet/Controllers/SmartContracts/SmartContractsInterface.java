package csd.wallet.Controllers.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/smartcontract")
public interface SmartContractsInterface {

    /**
     * @return Execute Smart Contracts
     */
    @PostMapping(value = "/execute")
    ResponseEntity<Result> executeSmartContract(
            @RequestBody SmartContract smartContract);

}
