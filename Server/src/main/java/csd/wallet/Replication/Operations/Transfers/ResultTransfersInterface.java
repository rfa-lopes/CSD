package csd.wallet.Replication.Operations.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Transfer;

public interface ResultTransfersInterface {

    Result addMoney(long accId, AddRemoveForm idAmount);

    Result removeMoney(long accId, AddRemoveForm idAmount);

    Result transfer(long accId, Transfer transfer);

    Result ledgerOfGlobalTransfers(long accId);

    Result ledgerOfWalletTransfers(long accId, long id);
}
