package csd.wallet.Replication.Operations.Transfers;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import csd.wallet.Utils.KeysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ErrorCode.*;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;
import static csd.wallet.Utils.KeysUtil.KeyType.HOMOADD;

@Service
public class ResultTransfersClass implements ResultTransfersInterface{

    @Autowired
    ServiceTransfersClass transfers;

    @Override
    public Result addMoney(long accId, AddRemoveForm idAmount, String keys) {

        KeysUtil keysUtil = new KeysUtil(keys);
        String addKey = keysUtil.getKey(HOMOADD);
        if(addKey == null)
            getError(BAD_REQUEST);

        try {
            transfers.addMoney(accId,idAmount,addKey);
            return (ok());
        } catch (InvalidAmountException e) {
            return (getError(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        } catch (AuthenticationErrorException e) {
            return getError(UNAUTHORIZED);
        }
    }

    @Override
    public Result removeMoney(long accId, AddRemoveForm idAmount, String keys) {

        KeysUtil keysUtil = new KeysUtil(keys);
        String addKey = keysUtil.getKey(HOMOADD);
        if(addKey == null)
            getError(BAD_REQUEST);

        try {
            transfers.removeMoney(accId,idAmount,addKey);
            return (ok());
        } catch (InvalidAmountException e) {
            return (getError(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        } catch (AuthenticationErrorException e) {
            return getError(UNAUTHORIZED);
        }
    }

    @Override
    public Result transfer(long accId, Transfer transfer, String keys) {

        KeysUtil keysUtil = new KeysUtil(keys);
        String addKey = keysUtil.getKey(HOMOADD);
        if(addKey == null)
            getError(BAD_REQUEST);

        try {
            transfers.transfer(accId,transfer,addKey);
            return (ok());
        } catch (InvalidAmountException | TransferToSameWalletException e) {
            return (getError(BAD_REQUEST));
        } catch (WalletNotExistsException e) {
            return (getError(NOT_FOUND));
        } catch (AuthenticationErrorException e) {
            return getError(UNAUTHORIZED);
        }
    }

    @Override
    public Result ledgerOfGlobalTransfers(long accId) {
        try {
            return ok(transfers.ledgerOfGlobalTransfers(accId));
        } catch (AuthenticationErrorException e) {
            return getError(UNAUTHORIZED);
        }
    }

    @Override
    public Result ledgerOfWalletTransfers(long accId, long id) {
        try {
            return ok(transfers.ledgerOfWalletTransfers(accId,id));
        } catch (WalletNotExistsException e) {
            return getError(NOT_FOUND);
        } catch (AuthenticationErrorException e) {
            return getError(UNAUTHORIZED);
        }
    }

    @Override
    public Result ledgerOfDateTransfers(long accId, String date) {
        try {
            return ok(transfers.ledgerOfDateTransfers(accId,date));
        } catch (AuthenticationErrorException e) {
            return getError(UNAUTHORIZED);
        }
    }
}
