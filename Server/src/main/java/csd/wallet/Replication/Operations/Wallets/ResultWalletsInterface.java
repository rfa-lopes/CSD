package csd.wallet.Replication.Operations.Wallets;

import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Wallet;

public interface ResultWalletsInterface {

    Result createWallet(Wallet wallet);

    Result deleteWallet(long id);

    Result getCurrentAmount(long id);

    Result getWalletInfo(long id);

}
