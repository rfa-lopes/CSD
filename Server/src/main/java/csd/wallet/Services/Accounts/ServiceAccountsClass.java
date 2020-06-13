package csd.wallet.Services.Accounts;

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

		if (accountRepository.findByUsername(account.getUsername()) != null)
			throw new AccountUsernameAlreadyExistsException();

		// Generate password hash
		account.hashPassword();

		Account acc = accountRepository.save(account);
		return acc.getId();
	}
}
