package CSD.Wallet.Services.SmartContracts;

import CSD.Wallet.Models.SignedResults;
import org.springframework.http.ResponseEntity;

public interface SmartContractServiceInter {

    ResponseEntity<SignedResults> execute (long owner, String pathToSmartContractJavaFile) throws Exception;
}
