package csd.wallet.Replication.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.Result;
import csd.wallet.Services.SmartContracts.ServiceSmartContractsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Result.error;
import static csd.wallet.Replication.Result.ok;

@Service
public class ResultSmartContractClass implements ResultSmartContractInterface{

    @Autowired
    ServiceSmartContractsClass smartconstracts;

    @Override
    public Result<Void> executeSmartContract(SmartContract smartContract) {
        try {
            smartconstracts.executeSmartContract(smartContract);
            return ok();
        } catch (Exception e) {
            return error(BAD_REQUEST);
        }
    }
}
