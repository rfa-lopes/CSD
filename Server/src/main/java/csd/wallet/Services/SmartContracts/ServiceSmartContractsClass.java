package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;

import csd.wallet.Utils.Logger;

import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Result.ok;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface {
	@Override
	public void executeSmartContract(SmartContract smartContract) {
		// TODO CLASSLOADER
		Logger.info("SERVICE: SMART CONTRACT EXECUTE");

		Class<?> smartcontractclass = null;
		try {
			// smartcontractclass =
			// InMemoryJavaCompiler.newInstance().compile(SmartContractClient.class.getName(),
			// smartContract.getCode());
			// SmartContractClient a =
			// (SmartContractClient)smartcontractclass.newInstance();
			// a.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
