package csd.wallet.Replication.Operations.Wallets;

import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Wallet;

public interface ResultWalletsInterface {

	Result createWallet(long accId, Wallet wallet);

	Result deleteWallet(long accId, long id);

	Result getCurrentAmount(long accId, long id);

	Result getWalletInfo(long accId, long id);

}
