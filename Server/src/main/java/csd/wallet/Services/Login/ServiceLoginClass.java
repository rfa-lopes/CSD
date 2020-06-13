package csd.wallet.Services.Login;

import org.springframework.beans.factory.annotation.Autowired;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Models.Account;
import csd.wallet.Repository.AccountRepository;
import csd.wallet.Utils.JWTUtil;

import org.springframework.stereotype.Service;

@Service
public class ServiceLoginClass implements ServiceLoginInterface {

	@Autowired
	private AccountRepository accs;

	@Override
	public String login(Account account) throws AuthenticationErrorException {

		long id = account.getId();
		String pw = account.getPassword();

		Account acc = accs.findById(id);

		if (acc == null || !acc.isValidPassword(pw))
			throw new AuthenticationErrorException();

		return JWTUtil.createJWT(id);
	}

}
