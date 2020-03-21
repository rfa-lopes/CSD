package csd.wallet.Controllers.Wallets;

import csd.wallet.Models.Wallet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = WalletsInter.BASE_URL)
public interface WalletsInter {

    String BASE_URL = "/wallets";

    String CREATE = "/create";
    String DELETE = "/delete/{id}";
    String AMOUNT = "/amount/{id}";
    String INFO   = "/info/{id}";

    /**
     * @Description
     * //TODO:
     * @param name: Username
     * @return
     *  OK, if wallet was created.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = CREATE,
            consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> createWallet (@RequestBody String name);

    /**
     * @Description
     * //TODO:
     * @param id: Wallet id.
     * @return
     *  OK, if wallet was deleted.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @DeleteMapping(
            value = DELETE)
    ResponseEntity<Void> deleteWallet (@PathVariable long id);

    /**
     * @Description
     * //TODO:
     * @param id: Wallet id.
     * @return
     *  OK and wallet current amount.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = AMOUNT,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Integer> getCurrentAmount (@PathVariable long id);

    /**
     * @Description
     * //TODO:
     * @param id: Wallet id.
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
