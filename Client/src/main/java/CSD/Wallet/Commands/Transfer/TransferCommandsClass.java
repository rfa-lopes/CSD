package CSD.Wallet.Commands.Transfer;

import CSD.Wallet.Services.Transfers.TransferServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class TransferCommandsClass implements TransferCommandsInter{

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
                return "Some wallet ID dont exist.";
            case 400:
                return "Bad request, try again.";
            default:
                return "Something went wrong.";
        }
    }
}
