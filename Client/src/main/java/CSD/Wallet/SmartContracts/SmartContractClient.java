package CSD.Wallet.SmartContracts;

import java.io.Serializable;

public class SmartContractClient implements SmartContractInterfaceClient, Serializable {

    @Override
    public void execute() {
        System.out.println("OLA FUI EXECUTADO");
    }

}