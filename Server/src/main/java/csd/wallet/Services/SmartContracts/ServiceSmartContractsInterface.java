package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;
import org.springframework.stereotype.Service;

import java.security.SignedObject;

@Service
public interface ServiceSmartContractsInterface {

    void executeSmartContract(SmartContract smartContract) throws Exception;

}
