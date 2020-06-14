package csd.wallet.Replication.Operations.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.Operations.Result;
import org.springframework.stereotype.Service;

@Service
public interface ResultSmartContractInterface {

    Result executeSmartContract(long accId, SmartContract smartContract);
}
