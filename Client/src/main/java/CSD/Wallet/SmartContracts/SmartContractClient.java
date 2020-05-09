package CSD.Wallet.SmartContracts;

import java.io.Serializable;

public class SmartContractClient implements SmartContractInterfaceClient, Serializable {

    public static void main(String[] args){
        new SmartContractClient().execute();
    }

    @Override
    public void execute() {
        System.out.println("OLA FUI EXECUTADO");
    }



}
