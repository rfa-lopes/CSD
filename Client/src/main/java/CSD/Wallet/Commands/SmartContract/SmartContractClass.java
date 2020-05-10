package CSD.Wallet.Commands.SmartContract;

import CSD.Wallet.Services.SmartContracts.SmartContractServiceClass;
import CSD.Wallet.Services.Transfers.TransferServiceInter;
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

    @Autowired
    public SmartContractClass(SmartContractServiceClass service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore",  env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }

    @Override
    @ShellMethod("Execute Smart Contract.")
    public String execute(
            @ShellOption({"-id", "-ownerid"}) long ownerId,
            @ShellOption({"-f", "-file"})String pathToSmartContractJavaFile) throws IOException {
        try {
            ResponseEntity<Void> resp = service.execute(ownerId, pathToSmartContractJavaFile);
            return "EXECUTED.";
        } catch (FileNotFoundException e) {
            return "File does not exist.";
        }
    }
}
