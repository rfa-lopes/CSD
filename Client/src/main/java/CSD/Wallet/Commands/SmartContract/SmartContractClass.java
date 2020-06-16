package CSD.Wallet.Commands.SmartContract;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
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
import java.util.List;

@ShellComponent
public class SmartContractClass implements SmartContractInterface {

    private final SmartContractServiceClass service;

    private static final String WRONG_SIGNATURE = "Wrong signatures.";
    private static final String SUCCESS = "Your smart contract was executed!";
    private static final String ACCESS_DENIED = "Your smart contract has unauthorized operations.";

    private static final String FILE_NOT_EXIST = "File does not exist.";


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

            switch (res.getError()) {
                case "OK":
                    return SUCCESS;
                case "ACCESS_DENIED":
                    return ACCESS_DENIED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return FILE_NOT_EXIST;
        }
        return null;
    }

}
