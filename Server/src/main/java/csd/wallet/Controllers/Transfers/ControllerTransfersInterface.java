package csd.wallet.Controllers.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.StringWrapper;
import csd.wallet.Models.Transfer;
import csd.wallet.Models.Wallet;
import csd.wallet.WebFilters.KeysFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = ControllerTransfersInterface.BASE_URL)
public interface ControllerTransfersInterface {

    String BASE_URL = "/transfers";

    String ADD_MONEY = "/add";
    String REMOVE_MONEY = "/remove";
    String TRANSFER = "/transfer";
    String GLOBAL = "/globaltransfers";
    String WALLET = "/wallettransfers/{id}";
    String DATE_TRANSFERS = "/datetransfers";

    /**
     * @param idAmount: Wallet id. | Amount to add to the wallet.
     * @return OK, if money is added.
     * NOT FOUND, if Wallet id does not exist.
     * BAD_REQUEST, if amount was invalid.
     * INTERNAL_SERVER_ERROR, if server error.
     * @Description
     */
    @PostMapping(
            value = ADD_MONEY)
    ResponseEntity<Void> addMoney(
            @RequestAttribute("id") long accId,
            @RequestBody AddRemoveForm idAmount,
            @RequestAttribute(KeysFilter.KEYS) String keys);

    /**
     * @param idAmount: Wallet id | Amount to remove from the wallet.
     * @return OK, if money is removed.
     * NOT FOUND, if Wallet id does not exist.
     * BAD_REQUEST, if amount was invalid.
     * INTERNAL_SERVER_ERROR, if server error.
     * @Description //TODO:
     */
    @PostMapping(
            value = REMOVE_MONEY)
    ResponseEntity<Void> removeMoney(
            @RequestAttribute("id") long accId,
            @RequestBody AddRemoveForm idAmount,
            @RequestAttribute(KeysFilter.KEYS) String keys);

    /**
     * @param transfer: Transference with: fromId, toId and amount.
     * @return OK, if the transfer is made.
     * NOT FOUND, if some Wallet id does not exist.
     * BAD_REQUEST, if amount was invalid or fromId equals to toId.
     * INTERNAL_SERVER_ERROR, if server error.
     * @Description //TODO: Tranferencias de A para A são impossiveis. (usar addMoney)
     */
    @PostMapping(
            value = TRANSFER)
    ResponseEntity<Void> transfer(
            @RequestAttribute("id") long accId,
            @RequestBody Transfer transfer,
            @RequestAttribute(KeysFilter.KEYS) String keys);

    /**
     * @return OK and List of all transfers.
     * INTERNAL_SERVER_ERROR, if server error.
     * @Description //TODO:
     */
    @GetMapping(
            value = GLOBAL,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transfer>> ledgerOfGlobalTransfers(@RequestAttribute("id") long accId);

    /**
     * @param id: Wallet id.
     * @return OK and List of all Wallet id transfers.
     * NOT FOUND, if Wallet id does not exist.
     * INTERNAL_SERVER_ERROR, if server error.
     * @Description //TODO:
     */
    @GetMapping(
            value = WALLET,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transfer>> ledgerOfWalletTransfers(@RequestAttribute("id") long accId,@PathVariable long id);



    /**
     * @return OK and List of all transfers.
     * INTERNAL_SERVER_ERROR, if server error.
     * @Description //TODO:
     */
    @PostMapping(
            value = DATE_TRANSFERS,consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<List<Transfer>> ledgerOfDateTransfers(@RequestAttribute("id") long accId,  @RequestBody StringWrapper date);

}
