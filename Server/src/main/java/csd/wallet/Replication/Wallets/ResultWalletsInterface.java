package csd.wallet.Replication.Wallets;

import csd.wallet.Replication.Result;
import csd.wallet.Models.Wallet;
import csd.wallet.Replication.ServiceProxy.SignedResult;

public interface ResultWalletsInterface {

    Result createWallet(Wallet wallet);

    Result deleteWallet(long id);

    Result getCurrentAmount(long id);

    Result getWalletInfo(long id);

}
