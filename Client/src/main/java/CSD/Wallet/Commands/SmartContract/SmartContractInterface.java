package CSD.Wallet.Commands.SmartContract;

import org.springframework.shell.standard.ShellComponent;

@ShellComponent
public interface SmartContractInterface {

    /**
     *
     * @param ownerId
     * @param pathToSmartContractJavaFile
     * @return OK
     */
    String execute(long ownerId, String pathToSmartContractJavaFile);

}
