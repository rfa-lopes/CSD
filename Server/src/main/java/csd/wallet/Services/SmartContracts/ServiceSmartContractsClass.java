package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.Utils.Logger;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface{
    @Override
    public void executeSmartContract(SmartContract smartContract) {
        File tmp = smartContract.getCode();

        Logger.error(tmp.getName());
        Logger.error(String.valueOf(tmp.length()));



    }
}
