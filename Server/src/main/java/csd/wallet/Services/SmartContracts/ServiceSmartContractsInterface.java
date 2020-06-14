package csd.wallet.Services.SmartContracts;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Models.SmartContract;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ServiceSmartContractsInterface {

    void executeSmartContract(long accId, SmartContract smartContract) throws AuthenticationErrorException, IOException, IllegalAccessException, InstantiationException;
}
