package csd.wallet.Replication.Operations.SmartContracts;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Services.SmartContracts.ServiceSmartContractsClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static csd.wallet.Replication.Operations.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Operations.Result.ErrorCode.UNAUTHORIZED;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultSmartContractClass implements ResultSmartContractInterface {

    @Autowired
    ServiceSmartContractsClass smartconstracts;

    @Override
    public Result executeSmartContract(long accId, SmartContract smartContract) {
        try {
            smartconstracts.executeSmartContract(accId, smartContract);
            return ok();
        } catch (AuthenticationErrorException authenticationErrorException) {
            return getError(UNAUTHORIZED);
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            return getError(BAD_REQUEST);
        }
    }
}
