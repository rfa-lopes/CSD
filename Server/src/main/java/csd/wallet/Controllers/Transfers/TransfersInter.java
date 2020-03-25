package csd.wallet.Controllers.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping(value = TransfersInter.BASE_URL)
public interface TransfersInter {

    String BASE_URL       = "/transfers";

    String ADD_MONEY      = "/add";
    String REMOVE_MONEY   = "/remove";
    String TRANSFER       = "/transfer";
    String GLOBAL         = "/globaltransfers";
    String WALLET         = "/wallettransfers/{id}";

    /**
     * @Description
     * @param idAmount: Wallet id. | Amount to add to the wallet.
     * @return
     *  OK, if money is added.
     *  NOT FOUND, if Wallet id does not exist.
     *  BAD_REQUEST, if amount was invalid.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = ADD_MONEY)
    ResponseEntity<Void> addMoney (@RequestBody AddRemoveForm idAmount);

    /**
     * @Description
     * //TODO:
     * @param idAmount: Wallet id | Amount to remove from the wallet.
     * @return
     *  OK, if money is removed.
     *  NOT FOUND, if Wallet id does not exist.
     *  BAD_REQUEST, if amount was invalid.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = REMOVE_MONEY)
    ResponseEntity<Void> removeMoney (@RequestBody AddRemoveForm idAmount);

    /**
     * @Description
     * //TODO: Tranferencias de A para A são impossiveis. (usar addMoney)
     * @param transfer: Transference with: fromId, toId and amount.
     * @return
     *  OK, if the transfer is made.
     *  NOT FOUND, if some Wallet id does not exist.
     *  BAD_REQUEST, if amount was invalid or fromId equals to toId.
     *  INTERNAL_SERVER_ERROR, if server error.
     */
    @PostMapping(
            value = TRANSFER)
    ResponseEntity<Void> transfer (@RequestBody Transfer transfer);

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
    ResponseEntity<ListWrapper> ledgerOfGlobalTransfers();

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
    ResponseEntity<ListWrapper> ledgerOfWalletTransfers(@PathVariable long id);


}
