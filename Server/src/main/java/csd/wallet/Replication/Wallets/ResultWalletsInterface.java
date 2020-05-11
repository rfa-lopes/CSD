package csd.wallet.Replication.Wallets;

import csd.wallet.Replication.Result;
import csd.wallet.Models.Wallet;
import csd.wallet.Replication.ServiceProxy.SignedResult;

public interface ResultWalletsInterface {

    SignedResult createWallet(Wallet wallet);

    SignedResult deleteWallet(long id);

    SignedResult getCurrentAmount(long id);

    SignedResult getWalletInfo(long id);

}
