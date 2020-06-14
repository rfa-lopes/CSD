package csd.wallet.Controllers.Accounts;

import csd.wallet.Models.Account;
import csd.wallet.Replication.Operations.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static csd.wallet.WebFilters.KeysFilter.KEYS;

@RestController
@RequestMapping(value = ControllerAccountsInterface.BASE_URL)
public interface ControllerAccountsInterface {

    String BASE_URL = "/accounts";

    String CREATE = "/create";

    @PostMapping(value = CREATE)
    <T> ResponseEntity<Result> createAccount(@RequestBody Account account);

}
