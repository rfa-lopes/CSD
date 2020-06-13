package csd.wallet.Replication.Operations.Login;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;

import csd.wallet.Replication.Operations.Result;
import csd.wallet.Models.Account;

import csd.wallet.Services.Login.ServiceLoginClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ErrorCode.*;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultLoginClass implements ResultLoginInterface {

	@Autowired
	ServiceLoginClass login;

	@Override
	public Result login(Account account) {
		// TODO Auto-generated method stub
		try {
			return (ok(login.login(account)));
		} catch (AuthenticationErrorException e) {
			return (getError(UNAUTHORIZED));
		}
	}

}
