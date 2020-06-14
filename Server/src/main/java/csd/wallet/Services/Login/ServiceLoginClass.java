package csd.wallet.Services.Login;

import csd.wallet.Exceptions.AccountsExceptions.InvalidAccountUsernameException;
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

        String username = account.getUsername();
        String password = account.getPassword();

        if (username.equals("") || password.equals(""))
            throw new AuthenticationErrorException();

        Account acc = accs.findByUsername(username);

        if (acc == null || !acc.isValidPassword(password))
            throw new AuthenticationErrorException();

        Account a = accs.save(account);

        return JWTUtil.createJWT(a.getId());
    }

}
