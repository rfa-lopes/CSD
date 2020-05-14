package CSD.Wallet.Commands.Transfer;

import CSD.Wallet.Models.Transfer;
import CSD.Wallet.Services.Transfers.TransferServiceInter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


@ShellComponent
public class TransferCommandsClass implements TransferCommandsInter{

    private static final String MESSAGE_404 = "Wallet ID doesn't exist.";
    private static final String MESSAGE_400 = "Bad request, try again.";
    private static final String MESSAGE_ERROR = "Something went wrong.";

    private static final String DELIMITER = "----------------------------";

    private static final int MAX_AMOUNT = 999999999; //Config file
    private static final int MIN_AMOUNT = 0; //Config file
    private static final String WRONG_SIGNATURE = "Wrong signatures.";

    private final TransferServiceInter service;

    @Autowired
    public TransferCommandsClass(TransferServiceInter service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore",  env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }

    @Override
    @ShellMethod("Transfer money to another wallet.")
    public String transfer(
            @ShellOption({"-f", "-fromId"}) long fromId,
            @ShellOption({"-t", "-toId"}) long toId,
            @ShellOption({"-a", "-amount"})  long amount) {

        if(fromId == toId)
            return "fromId should be different from toId.";

        if(amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            return "Incorrect amount.";

        HttpStatus status = service.transfer(fromId, toId, amount).getStatusCode();

        switch (status.value()){
            case 200:
                return "Transfer concluded.";
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("Add money to a wallet.")
    public String addAmount(
            @ShellOption({"-id"}) long id,
            @ShellOption({"-a", "-amount"})  long amount) {

        if(amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            return "Incorrect amount.";

        HttpStatus status = service.addAmount(id,amount).getStatusCode();

        switch (status.value()){
            case 200:
                return "Amount added";
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            case 422:
                return WRONG_SIGNATURE;
            default:
                return MESSAGE_ERROR;
        }
    }
    @Override
    @ShellMethod("Remove money from a wallet.")
    public String removeAmount(
            @ShellOption({"-id"}) long id,
            @ShellOption({"-a", "-amount"})  long amount) {

        if(amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            return "Incorrect amount.";

        HttpStatus status = service.removeAmount(id,amount).getStatusCode();

        switch (status.value()){
            case 200:
                return "Amount removed";
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("List all made transfers.")
    public String listGlobalTransfers() throws URISyntaxException {
        ResponseEntity<List<Transfer>> response = service.listGlobalTransfers();
        switch (response.getStatusCode().value()){
            case 200:
                return stringInfoTransfers(response.getBody());
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("List all transfers regarding wallet.")
    public String listWalletTransfers(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<List<Transfer>> response = service.listWalletTransfers(id);
        switch (response.getStatusCode().value()){
            case 200:
                return stringInfoTransfers(response.getBody());
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }

    private String stringInfoTransfers(List<Transfer> list){
        List<String> toPrint = new ArrayList<>();
        List<Transfer> arrayList = new ObjectMapper().convertValue(list, new TypeReference<List<Transfer>>(){});
        arrayList.forEach(transfer->toPrint.add(transfer.getInfo()));
        return String.join(DELIMITER,toPrint);
    }



}
