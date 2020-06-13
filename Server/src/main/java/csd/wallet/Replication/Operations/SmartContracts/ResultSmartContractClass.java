package csd.wallet.Replication.Operations.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.Operations.Result;
import csd.wallet.Services.SmartContracts.ServiceSmartContractsClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Operations.Result.getError;
import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultSmartContractClass implements ResultSmartContractInterface {

	@Autowired
	ServiceSmartContractsClass smartconstracts;

	@Override
	public Result executeSmartContract(SmartContract smartContract) {
		try {

			smartconstracts.executeSmartContract(smartContract);
			return ok();
		} catch (Exception e) {
			return getError(BAD_REQUEST);
		}
	}
}
