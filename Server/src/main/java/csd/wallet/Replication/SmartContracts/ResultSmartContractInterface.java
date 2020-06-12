package csd.wallet.Replication.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.Result;
import org.springframework.stereotype.Service;

@Service
public interface ResultSmartContractInterface {

    Result executeSmartContract(SmartContract smartContract);

}
