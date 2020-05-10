package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Utils.Logger;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface{
    @Override
    public void executeSmartContract(SmartContract smartContract) {
        String tmp = smartContract.getCode();
        Logger.error("Teste");
        Logger.error(String.valueOf(tmp.length()));



    }
}
