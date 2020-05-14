package csd.wallet.Replication.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Result;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.ServiceProxy.SignedResult;

import java.util.List;

public interface ResultTransfersInterface {

    Result addMoney(AddRemoveForm idAmount);

    Result removeMoney(AddRemoveForm idAmount);

    Result transfer(Transfer transfer) ;

    Result ledgerOfGlobalTransfers();

    Result ledgerOfWalletTransfers(long id);

}
