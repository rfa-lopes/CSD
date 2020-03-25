package CSD.Wallet.Commands.Wallet;

import CSD.Wallet.Models.Wallet;
import CSD.Wallet.Services.Wallets.WalletServiceInter;
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


        ResponseEntity<Long> response = service.create(name);

        switch (response.getStatusCodeValue()){
            case 200:
                return "New wallet created!" + "\n" + "Id:" + Long.toString(response.getBody());
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("Delete wallet.")
    public String delete(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<Long> response = service.getAmount(id);

        switch (response.getStatusCode().value()){
            case 200:
                service.delete(id);
                return "Wallet deleted!";
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }

    }

    @Override
    @ShellMethod("Get amount of money in wallet")
    public String getAmount(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<Long> response = service.getAmount(id);

        switch (response.getStatusCode().value()){
            case 200:
                return "Your wallet has the amount of:  " + Long.toString(response.getBody());
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("Get Wallet's information")
    public String getInfo(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<Wallet> response = service.getInfo(id);

        switch (response.getStatusCodeValue()){
            case 200:
                return "Your wallet's information:" + response.getBody().getInfo();
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }
}
