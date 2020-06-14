package csd.wallet.Controllers.Accounts;

import csd.wallet.Controllers.Login.ControllerLoginInterface;
import csd.wallet.Models.Account;
import csd.wallet.Replication.Operations.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ControllerAccountsInterface.BASE_URL)
public interface ControllerAccountsInterface {

    String BASE_URL = "/accounts";

    String CREATE = "/create";

    @PostMapping(value = CREATE)
    ResponseEntity<Result> createAccount( @RequestBody Account account);

}
