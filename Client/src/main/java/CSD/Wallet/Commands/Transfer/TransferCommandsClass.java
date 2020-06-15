package CSD.Wallet.Commands.Transfer;

import CSD.Wallet.Crypto.Utils.OnionBuilderOperation;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
import CSD.Wallet.Services.Transfers.TransferServiceInter;
import CSD.Wallet.Utils.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@ShellComponent
public class TransferCommandsClass implements TransferCommandsInter {

    private static final String MESSAGE_404 = "Wallet ID doesn't exist.";
    private static final String MESSAGE_400 = "Bad request, try again.";
    private static final String MESSAGE_ERROR = "Something went wrong.";

    private static final String DELIMITER = "----------------------------";

    private static final int MAX_AMOUNT = 999999999; //Config file
    private static final int MIN_AMOUNT = 0; //Config file
    private static final String WRONG_SIGNATURE = "Wrong signatures.";
    private static final String UNAUTHORIZED = "You don't have permissions to execute this operation!";
    private static final String MESSAGE_TIMEOUT = "Time out request.";

    private final TransferServiceInter service;

    @Autowired
    public TransferCommandsClass(TransferServiceInter service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore", env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }

    @Override
    @ShellMethod("Transfer money to another wallet.")
    public String transfer(
            @ShellOption({"-f", "-fromId"}) long fromId,
            @ShellOption({"-t", "-toId"}) long toId,
            @ShellOption({"-a", "-amount"}) long amount) {

        if (fromId == toId)
            return "fromId should be different from toId.";

        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT)
            return "Incorrect amount.";

        ResponseEntity<SignedResults> signedResults = service.transfer(fromId, toId, amount);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                return "Transfer concluded.";
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }

    }

    @Override
    @ShellMethod("Add money to a wallet.")
    public String addAmount(
            @ShellOption({"-id"}) long id,
            @ShellOption({"-a", "-amount"}) long amount) {

        //if (amount < MIN_AMOUNT || amount > MAX_AMOUNT)
        //   return "Incorrect amount.";

        if(amount <= 0)
            return "Incorrect amount.";

        ResponseEntity<SignedResults> signedResults = service.addAmount(id, amount);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                return "Amount added";
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("Remove money from a wallet.")
    public String removeAmount(
            @ShellOption({"-id"}) long id,
            @ShellOption({"-a", "-amount"}) long amount) {

        //if (amount < MIN_AMOUNT || amount > MAX_AMOUNT)
        //    return "Incorrect amount.";

        if(amount <= 0)
            return "Incorrect amount.";

        ResponseEntity<SignedResults> signedResults = service.removeAmount(id, amount);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                return "Amount removed";
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("List all made transfers.")
    public String listGlobalTransfers() throws URISyntaxException {
        ResponseEntity<SignedResults> signedResults = service.listGlobalTransfers();

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                return stringInfoTransfers((List<Transfer>) res.getResult());
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("List all transfers regarding wallet.")
    public String listWalletTransfers(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.listWalletTransfers(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {

            case "OK":
                return stringInfoTransfers((List<Transfer>) res.getResult());
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }
    }

    private String stringInfoTransfers(List<Transfer> list) {

        List<String> toPrint = new ArrayList<>();
        List<Transfer> arrayList = new ObjectMapper().convertValue(list, new TypeReference<List<Transfer>>() {
        });
        arrayList.forEach(transfer -> {
            BigInteger amount_add = new BigInteger(transfer.getAmount_add());
            BigInteger amount = OnionBuilderOperation.decryptHomoAdd(amount_add);
            transfer.setAmount_add(amount + "");
            toPrint.add(transfer.getInfo());
        });
        return String.join(DELIMITER, toPrint);
    }


}
