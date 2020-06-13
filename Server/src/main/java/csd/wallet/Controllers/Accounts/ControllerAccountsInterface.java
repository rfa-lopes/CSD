package csd.wallet.Controllers.Accounts;

import csd.wallet.Models.Account;
import csd.wallet.Replication.Operations.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts")
public interface ControllerAccountsInterface {


    @PostMapping(value = "/create")
    ResponseEntity<Result> createAccount( @RequestBody Account account);

}
