package csd.wallet.Replication.Operations.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Transfer;

public interface ResultTransfersInterface {

    Result addMoney(long accId, AddRemoveForm idAmount, String keys);

    Result removeMoney(long accId, AddRemoveForm idAmount, String keys);

    Result transfer(long accId, Transfer transfer, String keys);

    Result ledgerOfGlobalTransfers(long accId);

    Result ledgerOfWalletTransfers(long accId, long id);
}
