package csd.wallet.Services.Accounts;

import csd.wallet.Crypto.OnionBuilder;
import csd.wallet.Exceptions.AccountsExceptions.AccountUsernameAlreadyExistsException;
import csd.wallet.Exceptions.AccountsExceptions.InvalidAccountUsernameException;
import csd.wallet.Models.Account;
import csd.wallet.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceAccountsClass implements ServiceAccountsInterface {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public long create(Account account) throws AccountUsernameAlreadyExistsException, InvalidAccountUsernameException {

		if (account.getUsername().equals("") || account.getUsername() == null)
			throw new InvalidAccountUsernameException();

		String RND_DET_username = account.getUsername();

		if (accountRepository.findByUsername(RND_DET_username) != null)
			throw new AccountUsernameAlreadyExistsException();

		// Generate password hash
		account.hashPassword();

		Account acc = accountRepository.save(account);
		return acc.getId();
	}
}
