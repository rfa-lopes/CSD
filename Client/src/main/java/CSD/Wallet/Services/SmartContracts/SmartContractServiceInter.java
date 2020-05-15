package CSD.Wallet.Services.SmartContracts;

import org.springframework.http.ResponseEntity;

public interface SmartContractServiceInter {

    ResponseEntity<Void> execute (long owner, String pathToSmartContractJavaFile) throws Exception;
}
