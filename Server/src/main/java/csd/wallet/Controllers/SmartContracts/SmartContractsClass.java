package csd.wallet.Controllers.SmartContracts;

import csd.wallet.Controllers.RestResource;
import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.BFTClient;
import csd.wallet.Services.SmartContracts.ServiceSmartContractsInterface;
import csd.wallet.SmartContracts.SmartContractClient;
import csd.wallet.Utils.Logger;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Base64;
import java.util.List;

import static csd.wallet.Replication.Result.ok;

@RestController
public class SmartContractsClass extends RestResource implements SmartContractsInterface {

    @Autowired
    BFTClient bftClient;

    @Override
    public ResponseEntity<Void> executeSmartContract(SmartContract smartContract) {
        Logger.info("Request: SMART CONTRACT EXECUTE");

        byte[] sourceCodeByte = Base64.getDecoder().decode(smartContract.getCode());
        String sourceCode = new String(sourceCodeByte);

        //Logger.warn(smartContract.getCode()); //ESTA CERTO
        Logger.error(sourceCode); //ESTA CERTO

        Class<?> smartcontractclass = null;
        try {


            Logger.replication("OLA1");
            smartcontractclass = InMemoryJavaCompiler.newInstance().compile(SmartContractClient.class.getName(), sourceCode);
            if( smartcontractclass.getMethod("execute") == null)
                Logger.error("method is null"); // nao ta a printar, o que é bom

            SmartContractClient a = (SmartContractClient)smartcontractclass.newInstance();
            a.execute();
            // smartcontractclass.getMethod("execute").invoke(smartcontractclass.getDeclaredConstructor().newInstance()); nao printa
            // smartcontractclass.getMethod("execute").invoke(smartcontractclass.newInstance()); nao printa


            Logger.replication("OLA2");



            /*
            Desnecessario?
            SmartContractClient a = (SmartContractClient)smartcontractclass.newInstance();
            Logger.replication("OLA3");
            a.execute();

             */




        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getResponse(ok());
        //return super.getResponse(bftClient.getInvoke(RequestType.SMART_CONTRACT_EXECUTE, TOMMessageType.ORDERED_REQUEST, smartContract));
    }
}
