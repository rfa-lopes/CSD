package CSD.Wallet.Commands.Transfer;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.net.URISyntaxException;

@ShellComponent
public interface TransferCommandsInter {

    /**
     * Description
     * TODO
     * @param fromId TODO
     * @param toId TODO
     * @param amount TODO
     * @return TODO
     */
    String transfer(long fromId, long toId, long amount);

    /**
     * Description
     * Adds amount of money to wallet with given id
     * @param id Wallet's id
     * @param amount Amount of money to be added
     * @return Status code of operation
     */
    String addAmount(long id, long amount);

    /**
     * Description
     * Removes amount of money to wallet with given id
     * @param id Wallet's id
     * @param amount Amount of money to be removed
     * @return Status code of operation
     */
    String removeAmount(long id, long amount);


    /**
     * Description
     * Lists all made transfers
     * @return Status code of operation
     */
    String listGlobalTransfers() throws URISyntaxException;

    /**
     * Description
     * Lists all made transfers regarding the wallet with the given ID
     * @param id Wallet's id
     * @return Status code of operation
     */
    String listWalletTransfers(long id) throws URISyntaxException;



}
