package csd.wallet.Controllers.Wallets;

import csd.wallet.Models.Wallet;
import csd.wallet.Replication.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = ControllerWalletsInterface.BASE_URL)
public interface ControllerWalletsInterface {

    String BASE_URL = "/wallets";

    String CREATE = "/create";
    String DELETE = "/delete/{id}";
    String AMOUNT = "/amount/{id}";
    String INFO   = "/info/{id}";

    /**
     * @Description
     * //TODO:
     * @param wallet -> Wallet
     * @return
     *  OK, if wallet was created, and wallet id generated.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = CREATE,
            consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Result> createWallet (@RequestBody Wallet wallet);

    /**
     * @Description
     * //TODO:
     * @param id -> Wallet id.
     * @return
     *  NO_CONTENT, if wallet was deleted.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = DELETE)
    ResponseEntity<Void> deleteWallet (@PathVariable long id);

    /**
     * @Description
     * //TODO:
     * @param id -> Wallet id.
     * @return
     *  OK and wallet current amount.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = AMOUNT,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Long> getCurrentAmount (@PathVariable long id);

    /**
     * @Description
     * //TODO:
     * @param id -> Wallet id.
     * @return
     *  OK and all wallet information.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = INFO,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Wallet> getWalletInfo (@PathVariable long id);

}
