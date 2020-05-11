package csd.wallet.Replication.Transfers;

import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Replication.Result;
import csd.wallet.Models.Transfer;
import csd.wallet.Replication.ServiceProxy.SignedResult;

import java.util.List;

public interface ResultTransfersInterface {

    SignedResult addMoney(AddRemoveForm idAmount);

    SignedResult removeMoney(AddRemoveForm idAmount);

    SignedResult transfer(Transfer transfer) ;

    SignedResult ledgerOfGlobalTransfers();

    SignedResult ledgerOfWalletTransfers(long id);

}
