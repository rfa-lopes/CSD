package CSD.Wallet.Services.SmartContracts;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

public interface SmartContractServiceInter {

    ResponseEntity<Void> execute (long owner, String pathToSmartContractJavaFile) throws FileNotFoundException, FileUploadException;
}
