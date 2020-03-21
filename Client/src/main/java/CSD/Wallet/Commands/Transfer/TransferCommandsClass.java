package CSD.Wallet.Commands.Transfer;

import CSD.Wallet.Models.TransferModel1;
import CSD.Wallet.Services.Transfers.TransferServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@ShellComponent
public class TransferCommandsClass implements TransferCommandsInter{

    private static final String MESSAGE_404 = "Wallet ID doesn't exist.";
    private static final String MESSAGE_400 = "Bad request, try again.";
    private static final String MESSAGE_ERROR = "Something went wrong.";

    private final TransferServiceInter service;

    @Autowired
    public TransferCommandsClass(TransferServiceInter service) {
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

        if(amount < 0 || amount > 999999999)
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

        if(amount < 0 || amount > 999999999)
            return "Incorrect amount.";

        HttpStatus status = service.addAmount(id,amount).getStatusCode();

        switch (status.value()){
            case 200:
                return "Amount added";
            case 404:
                return MESSAGE_404;
            case 400:
                return MESSAGE_400;
            default:
                return MESSAGE_ERROR;
        }
    }
    @Override
    @ShellMethod("Remove money from a wallet.")
    public String removeAmount(
            @ShellOption({"-id"}) long id,
            @ShellOption({"-a", "-amount"})  long amount) {

        if(amount < 0 || amount > 999999999)
            return "Incorrect amount.";

        HttpStatus status = service.addAmount(id,amount).getStatusCode();

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
    public String listGlobalTransfers() {
        ResponseEntity<List<TransferModel1>> response = service.listGlobalTransfers();
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
            @ShellOption({"-id"}) long id) {

        ResponseEntity<List<TransferModel1>> response = service.listWalletTransfers(id);
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

    private String stringInfoTransfers(List<TransferModel1> list){
        List<String> toPrint = new ArrayList<>();
        list.forEach(transfer->toPrint.add(transfer.toString()));
        return String.join(",",toPrint);
    }



}
