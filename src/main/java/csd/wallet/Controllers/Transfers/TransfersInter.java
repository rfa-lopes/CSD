package csd.wallet.Controllers.Transfers;

import csd.wallet.Controllers.Wallets.WalletsInter;
import csd.wallet.Models.Transfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = TransfersInter.BASE_URL)
public interface TransfersInter {

    String BASE_URL       = "/transfers";

    String ADD_MONEY      = "/add/{id}/{amount}";
    String REMOVE_MONEY   = "/remove/{id}/{amount}";
    String TRANSFER       = "/transfer/{fromId}/{toId}/{amount}";
    String GLOBAL         = "/globaltransfers";
    String WALLET         = "/walletltransfers/{id}";

    /**
     * @Description
     * @param id: Wallet id.
     * @param amount: Amount to add to the wallet.
     * @return
     *  OK, if money is added.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = ADD_MONEY)
    ResponseEntity<Void> addMoney (@PathVariable long id, @PathVariable long amount);

    /**
     * @Description
     * //TODO:
     * @param id: Wallet id.
     * @param amount: Amount to remove from the wallet.
     * @return
     *  OK, if money is removed.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = REMOVE_MONEY)
    ResponseEntity<Void> removeMoney (@PathVariable long id, @PathVariable long amount);

    /**
     * @Description
     * //TODO:
     * @param fromId: From wallet id.
     * @param toId: To wallet id.
     * @param amount: Transfer amount.
     * @return
     *  OK, if the transfer is made.
     *  NOT FOUND, if some Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = TRANSFER)
    ResponseEntity<Void> transfer (@PathVariable long fromId, @PathVariable long toId, @PathVariable long amount);

    /**
     * @Description
     * //TODO:
     * @return
     *  OK and List of all transfers.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = GLOBAL,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transfer>> ledgerOfGlobalTransfers();

    /**
     * @Description
     * //TODO:
     * @param id: Wallet id.
     * @return
     *  OK and List of all Wallet id transfers.
     *  NOT FOUND, if Wallet id does not exist.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @GetMapping(
            value = WALLET,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transfer>> LedgerOfWalletTransfers(@PathVariable long id);


}
