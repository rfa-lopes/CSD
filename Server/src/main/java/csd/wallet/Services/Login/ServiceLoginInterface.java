package csd.wallet.Services.Login;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;

import csd.wallet.Models.Account;

import org.springframework.stereotype.Service;

@Service
public interface ServiceLoginInterface {

	String login(Account account) throws AuthenticationErrorException;

}
