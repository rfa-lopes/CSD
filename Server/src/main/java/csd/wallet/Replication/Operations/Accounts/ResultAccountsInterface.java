package csd.wallet.Replication.Operations.Accounts;

import csd.wallet.Models.Account;
import csd.wallet.Replication.Operations.Result;
import org.springframework.stereotype.Service;

@Service
public interface ResultAccountsInterface {
    Result createAccount(Account account);
}
