package csd.wallet.Replication.Operations.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Operations.Result.ErrorCode.NOT_FOUND;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultTransfersClass implements ResultTransfersInterface{

    @Autowired
    ServiceTransfersClass transfers;

    @Override
    public Result addMoney(AddRemoveForm idAmount) {
        try {
            transfers.addMoney(idAmount);
            return (ok());
        } catch (InvalidAmountException e) {
            return (getError(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        }
    }

    public Result removeMoney(AddRemoveForm idAmount) {
        try {
            transfers.removeMoney(idAmount);
            return (ok());
        } catch (InvalidAmountException e) {
            return (getError(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        }
    }

    @Override
    public Result transfer(Transfer transfer) {
        try {
            transfers.transfer(transfer);
            return (ok());
        } catch (InvalidAmountException | TransferToSameWalletException e) {
            return (getError(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        }
    }

    @Override
    public Result ledgerOfGlobalTransfers() {
        return ok(transfers.ledgerOfGlobalTransfers());
    }

    @Override
    public Result ledgerOfWalletTransfers(long id) {
        try {
            return ok(transfers.ledgerOfWalletTransfers(id));
        } catch (WalletNotExistsException e) {
            return getError(NOT_FOUND);
        }
    }
}
