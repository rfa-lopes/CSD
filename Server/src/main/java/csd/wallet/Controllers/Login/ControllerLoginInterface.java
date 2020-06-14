package csd.wallet.Controllers.Login;

import csd.wallet.Models.Account;

import csd.wallet.Replication.Operations.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = ControllerLoginInterface.BASE_URL)
public interface ControllerLoginInterface {

	String BASE_URL = "/accounts";

	String LOGIN = "/login";

	/**
	 * @Description //TODO:
	 * @param wallet -> Wallet
	 * @return OK, if wallet was created, and wallet id generated.
	 *         INTERNAL_SERVER_ERROR, if server error.
	 */
	@PostMapping(value=LOGIN, consumes = APPLICATION_JSON_VALUE)
	ResponseEntity<Result> login(@RequestBody Account account);

}
