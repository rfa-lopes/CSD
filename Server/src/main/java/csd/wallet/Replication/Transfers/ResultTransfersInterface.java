package csd.wallet.Replication.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Result;
import csd.wallet.Models.Transfer;

import java.util.List;

public interface ResultTransfersInterface {

    Result<Void> addMoney(AddRemoveForm idAmount);

    Result<Void> removeMoney(AddRemoveForm idAmount);

    Result<Void> transfer(Transfer transfer) ;

    Result<List<Transfer>> ledgerOfGlobalTransfers();

    Result<List<Transfer>> ledgerOfWalletTransfers(long id);

}
