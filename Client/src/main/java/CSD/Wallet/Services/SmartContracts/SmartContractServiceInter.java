package CSD.Wallet.Services.SmartContracts;

import CSD.Wallet.Utils.DynamicCompilerException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SmartContractServiceInter {

    ResponseEntity<Void> execute (long owner, String pathToSmartContractJavaFile) throws Exception;
}
