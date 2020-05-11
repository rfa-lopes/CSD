package csd.wallet.Replication.Transfers;

import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Result.ErrorCode.NOT_FOUND;
import static csd.wallet.Replication.Result.error;
import static csd.wallet.Replication.Result.ok;
import static csd.wallet.Replication.ServiceProxy.SignedResult.createSignedResult;

@Service
public class ResultTransfersClass implements ResultTransfersInterface{

    @Autowired
    ServiceTransfersClass transfers;

    @Override
    public SignedResult addMoney(AddRemoveForm idAmount) {
        try {
            transfers.addMoney(idAmount);
            return createSignedResult(ok());
        } catch (InvalidAmountException e) {
            return createSignedResult(error(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }

    public SignedResult removeMoney(AddRemoveForm idAmount) {
        try {
            transfers.removeMoney(idAmount);
            return createSignedResult(ok());
        } catch (InvalidAmountException e) {
            return createSignedResult(error(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }

    @Override
    public SignedResult transfer(Transfer transfer) {
        try {
            transfers.transfer(transfer);
            return createSignedResult(ok());
        } catch (InvalidAmountException | TransferToSameWalletException e) {
            return createSignedResult(error(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }

    @Override
    public SignedResult ledgerOfGlobalTransfers() {
        return createSignedResult(ok(transfers.ledgerOfGlobalTransfers()));
    }

    @Override
    public SignedResult ledgerOfWalletTransfers(long id) {
        try {
            return createSignedResult(ok(transfers.ledgerOfWalletTransfers(id)));
        } catch (WalletNotExistsException e) {
            return createSignedResult(error(NOT_FOUND));
        }
    }
}
