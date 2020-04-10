package csd.wallet.Replication.Wallets;

import csd.wallet.Replication.Result;
import csd.wallet.Models.Wallet;

public interface ResultWalletsInterface {

    Result<Long> createWallet(Wallet wallet);

    Result<Void> deleteWallet(long id);

    Result<Long> getCurrentAmount(long id);

    Result<Wallet> getWalletInfo(long id);

}
