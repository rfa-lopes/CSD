package CSD.Wallet.Commands.Wallet;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Wallet;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import CSD.Wallet.Services.Wallets.WalletServiceInter;
import CSD.Wallet.Utils.Result;
import CSD.Wallet.Utils.VerifySignatures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.net.URISyntaxException;

@ShellComponent
public class WalletCommandsClass implements WalletCommandsInter{

    private static final String MESSAGE_404 = "Wallet ID doesn't exist.";
    private static final String MESSAGE_400 = "Bad request, try again.";
    private static final String MESSAGE_ERROR = "Something went wrong.";
    private static final String WRONG_SIGNATURE = "Wrong signatures.";
    private static final String FAILED_AUTH = "You don't have permissions to execute this operation!";
    private static final String MESSAGE_TIMEOUT = "Time out request.";

    private final WalletServiceInter service;

    @Autowired
    public WalletCommandsClass(WalletServiceInter service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore",  env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }


    @Override
    @ShellMethod("Create new wallet.")
    public String create(
            @ShellOption({"-n", "-name"}) String name) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.create(name);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if(VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()){
            case "OK":
                int id = (Integer)res.getResult();
                return "New wallet created!" + "\n" + "Id:" + id;
            default: return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("Delete wallet.")
    public String delete(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.delete(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if(VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()){
            case "OK": return "Wallet deleted!";
            case "BAD_REQUEST": return MESSAGE_400;
            case "NOT_FOUND": return MESSAGE_404;
            case "TIME_OUT": return MESSAGE_TIMEOUT;
            case "FORBIDDEN": return FAILED_AUTH;
            default: return MESSAGE_ERROR;
        }

    }

    @Override
    @ShellMethod("Get amount of money in wallet")
    public String getAmount(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.getAmount(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if(VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()){
            case "OK": return "Your wallet has the amount of:  " + (Integer)res.getResult();
            case "BAD_REQUEST": return MESSAGE_400;
            case "NOT_FOUND": return MESSAGE_404;
            case "TIME_OUT": return MESSAGE_TIMEOUT;
            case "FORBIDDEN": return FAILED_AUTH;
            default: return MESSAGE_ERROR;
        }

    }

    @Override
    @ShellMethod("Get Wallet's information")
    public String getInfo(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.getInfo(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if(VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        ObjectMapper mapper = new ObjectMapper();
        Wallet result = mapper.convertValue(res.getResult(), Wallet.class);

        switch (res.getError()){
            case "OK": return "Your wallet's information:" + result.getInfo();
            case "BAD_REQUEST": return MESSAGE_400;
            case "NOT_FOUND": return MESSAGE_404;
            case "TIME_OUT": return MESSAGE_TIMEOUT;
            case "FORBIDDEN": return FAILED_AUTH;
            default: return MESSAGE_ERROR;
        }

    }

}
