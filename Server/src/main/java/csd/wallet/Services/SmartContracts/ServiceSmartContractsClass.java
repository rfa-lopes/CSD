package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;
import csd.wallet.SmartContracts.SmartContractClient;
import csd.wallet.SmartContracts.SmartContractInterfaceClient;
import csd.wallet.Utils.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

import static csd.wallet.Replication.Result.ErrorCode.BAD_REQUEST;
import static csd.wallet.Replication.Result.error;
import static csd.wallet.Replication.Result.ok;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface{
    @Override
    public void executeSmartContract(SmartContract smartContract) {
        //TODO CLASSLOADER
        Logger.info("SERVICE: SMART CONTRACT EXECUTE");

        Class<?> smartcontractclass = null;
        try {
            smartcontractclass = InMemoryJavaCompiler.newInstance().compile(SmartContractClient.class.getName(), smartContract.getCode());
            SmartContractClient a = (SmartContractClient)smartcontractclass.newInstance();
            a.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
