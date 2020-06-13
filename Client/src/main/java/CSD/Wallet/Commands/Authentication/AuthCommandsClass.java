package CSD.Wallet.Commands.Authentication;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import CSD.Wallet.Services.Authentication.AuthServiceInter;
import CSD.Wallet.Utils.Result;
import CSD.Wallet.Utils.VerifySignatures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class AuthCommandsClass implements AuthCommandInter {

    private static final String MESSAGE_404 = "Wallet ID doesn't exist.";
    private static final String MESSAGE_400 = "Bad request, try again.";
    private static final String MESSAGE_ERROR = "Something went wrong.";

    private static final String DELIMITER = "----------------------------";

    private static final int MAX_AMOUNT = 999999999; //Config file
    private static final int MIN_AMOUNT = 0; //Config file
    private static final String WRONG_SIGNATURE = "Wrong signatures.";
    private static final String UNAUTHORIZED = "You don't have permissions to execute this operation!";
    private static final String MESSAGE_TIMEOUT = "Time out request.";
    private static final String BAD_REQUEST = "Invalid parameters." ;

    private final AuthServiceInter service;

    @Autowired
    public AuthCommandsClass(AuthServiceInter service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore",  env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }



    @Override
    @ShellMethod("Login")
    public String login(
            @ShellOption({"-u", "-username"}) String username,
            @ShellOption({"-pw", "-password"}) String password) {

        ResponseEntity<SignedResults> signedResults = service.login(username, password);
        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                LocalRepo.setJWT((String)res.getResult());
                return "User is now logged!";
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("create")
    public String create(
            @ShellOption({"-u", "-username"}) String username,
            @ShellOption({"-pw", "-password"}) String password) {

        ResponseEntity<SignedResults> signedResults = service.login(username, password);
        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                return "" + (long)res.getResult();
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "BAD_REQUEST":
                return BAD_REQUEST;
            default:
                return MESSAGE_ERROR;
        }
    }

}
