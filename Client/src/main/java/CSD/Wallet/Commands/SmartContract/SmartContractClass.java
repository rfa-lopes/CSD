package CSD.Wallet.Commands.SmartContract;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Services.SmartContracts.SmartContractServiceClass;
import CSD.Wallet.Services.Transfers.TransferServiceInter;
import CSD.Wallet.Utils.Result;
import CSD.Wallet.Utils.VerifySignatures;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@ShellComponent
public class SmartContractClass implements SmartContractInterface {

    private final SmartContractServiceClass service;

    private static final String WRONG_SIGNATURE = "Wrong signatures.";
    private static final String MESSAGE_TIMEOUT = "Time out request.";

    @Autowired
    public SmartContractClass(SmartContractServiceClass service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore", env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }

    @Override
    @ShellMethod("Execute Smart Contract.")
    public String execute(
            @ShellOption({"-id", "-ownerid"}) long ownerId,
            @ShellOption({"-f", "-file"}) String pathToSmartContractJavaFile) {
        try {
            ResponseEntity<SignedResults> signedResults = service.execute(ownerId, pathToSmartContractJavaFile);
            SignedResults s = signedResults.getBody();
            Result res = s.getResult();

            if (VerifySignatures.verify(s.getSignatureReceive(), res))
                return WRONG_SIGNATURE;

            return "EXECUTED.";
        } catch (Exception e) {
            //e.printStackTrace();
            return "File does not exist.";
        }
    }
}
