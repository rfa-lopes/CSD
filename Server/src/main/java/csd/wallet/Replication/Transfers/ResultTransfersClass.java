package csd.wallet.Replication.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Result;
import csd.wallet.Models.Transfer;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static csd.wallet.Replication.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Result.ErrorCode.NOT_FOUND;
import static csd.wallet.Replication.Result.error;
import static csd.wallet.Replication.Result.ok;

@Service
public class ResultTransfersClass implements ResultTransfersInterface{

    @Autowired
    ServiceTransfersClass transfers;


    @Override
    public Result<Void> addMoney(AddRemoveForm idAmount) {
        try {
            transfers.addMoney(idAmount);
            return ok();
        } catch (InvalidAmountException e) {
            return error(BAD_REQUEST);
        } catch (WalletNotExistsException e) {
            return error(NOT_FOUND);
        }
    }

    @Override
    public Result<Void> removeMoney(AddRemoveForm idAmount) {
        try {
            transfers.removeMoney(idAmount);
            return ok();
        } catch (InvalidAmountException e) {
            return error(BAD_REQUEST);
        } catch (WalletNotExistsException e) {
            return error(NOT_FOUND);
        }
    }

    @Override
    public Result<Void> transfer(Transfer transfer) {
        try {
            transfers.transfer(transfer);
            return ok();
        } catch (InvalidAmountException | TransferToSameWalletException e) {
            return error(BAD_REQUEST);
        } catch (WalletNotExistsException e) {
            return error(NOT_FOUND);
        }
    }

    @Override
    public Result<List<Transfer>> ledgerOfGlobalTransfers() {
        return ok(transfers.ledgerOfGlobalTransfers());
    }

    @Override
    public Result<List<Transfer>> ledgerOfWalletTransfers(long id) {
        try {
            return ok(transfers.ledgerOfWalletTransfers(id));
        } catch (WalletNotExistsException e) {
            return error(NOT_FOUND);
        }
    }
}
