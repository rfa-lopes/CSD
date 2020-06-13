package csd.wallet.Replication.Operations.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Transfer;

public interface ResultTransfersInterface {

    Result addMoney(AddRemoveForm idAmount);

    Result removeMoney(AddRemoveForm idAmount);

    Result transfer(Transfer transfer) ;

    Result ledgerOfGlobalTransfers();

    Result ledgerOfWalletTransfers(long id);

}
