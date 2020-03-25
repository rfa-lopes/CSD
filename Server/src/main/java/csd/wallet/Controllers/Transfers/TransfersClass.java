package csd.wallet.Controllers.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.WalletRepository;
import csd.wallet.Utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import csd.wallet.Repository.TransferRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TransfersClass implements TransfersInter {

    @Autowired
    private TransferRepository transfers;

    @Autowired
    private WalletRepository wallets;

    private Log log = Log.getInstance(TransfersClass.class);

    public static long MIN_AMOUNT = 0;
    public static long MAX_AMOUNT = 999999999;

    @Override
    public ResponseEntity<Void> addMoney(AddRemoveForm idAmount) {

        long id = idAmount.getId();
        long amount = idAmount.getAmount();

        try {
            //Input restrictions
            AmountRestrictions(amount);

            Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));

            w.addAmount(amount);
            wallets.save(w);
            log.info("AddMoney: %d -> %d", amount, id);
            return ResponseEntity.ok().build();

        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAmountException e){
            return ResponseEntity.badRequest().build();
        } catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> removeMoney(AddRemoveForm idAmount) {

        long id = idAmount.getId();
        long amount = idAmount.getAmount();

        try {
            //Input restrictions
            AmountRestrictions(amount);

            Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));

            //Can't remove more than what he have.
            long availableAmount = w.getAmount();
            if(availableAmount - amount < 0)
                throw new InvalidAmountException(availableAmount);

            w.removeAmount(amount);
            wallets.save(w);
            log.info("RemoveMoney: %d -> %d", amount, id);
            return ResponseEntity.ok().build();

        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAmountException e){
            return ResponseEntity.badRequest().build();
        } catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> transfer(Transfer transfer) {
        try {
            long amount = transfer.getAmount();
            long fromId = transfer.getFromId();
            long toId = transfer.getToId();

            //Input restrictions
            AmountRestrictions(amount);

            Wallet fromW = wallets.findById(fromId).orElseThrow(() -> new WalletNotExistsException(fromId));
            Wallet toW = wallets.findById(toId).orElseThrow(() -> new WalletNotExistsException(toId));

            //Can't transfer money to the same wallet.
            if(fromW == toW)
                throw new TransferToSameWalletException(fromId);

            //Can't remove more than what he have.
            long fromAmount = fromW.getAmount();
            if(fromAmount - amount < 0)
                throw new InvalidAmountException(fromAmount);

            fromW.removeAmount(amount);
            toW.addAmount(amount);

            transfers.save(new Transfer(fromId, toId, amount));
            wallets.save(fromW);
            wallets.save(toW);
            log.info("Transfer: %d -> %d (%d)", fromId, toId, amount);
            return ResponseEntity.ok().build();

        } catch (WalletNotExistsException e) {
            return ResponseEntity.notFound().build();
        } catch (InvalidAmountException | TransferToSameWalletException e){
            return ResponseEntity.badRequest().build();
        } catch(Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ListWrapper> ledgerOfGlobalTransfers() {
        try{
            List<Transfer> globalTransfers = new ArrayList();
            transfers.findAll().forEach(globalTransfers::add);
            log.info("LedgerOfGlobalTransfers");
            return ResponseEntity.ok(new ListWrapper(globalTransfers));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ListWrapper> ledgerOfWalletTransfers(long id) {
        try{
            System.out.println(Long.toString(id));
            List<Transfer> walletTransfers = transfers.findAllByFromId(id);
            List<Transfer> toT = transfers.findAllByToId(id);
            walletTransfers.addAll(toT);
            log.info("LedgerOfWalletTransfers: %d", id);
            return ResponseEntity.ok(new ListWrapper(walletTransfers));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
        }
    }

    private void AmountRestrictions(long amount) throws InvalidAmountException {
        if(amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            throw new InvalidAmountException();
    }
}
