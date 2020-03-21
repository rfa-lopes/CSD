package CSD.Wallet.Commands.Wallet;

import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface WalletCommandsInter {

    /**
     * Description
     * Creates a new wallet
     * @param name Name of the wallet's owner
     * @return Status code of operation
     */
    String create(String name) throws URISyntaxException;

    /**
     * Description
     * Deletes the wallet with the given id
     * @param id Wallet's id
     * @return Status code of operation
     */
    String delete(long id) throws URISyntaxException;


    /**
     * Description
     * Gets amount of money in the wallet with the given id
     * @param id Wallet's id
     * @return Amount of money in the wallet if succeeded
     */
    String getAmount(long id) throws URISyntaxException;


    /**
     * Description
     * Gets information regarding the wallet (Owner of wallet, amount of money and wallet id)
     * @param id Wallet's id
     * @return Wallet's information if succeeded
     */
    String getInfo(long id) throws URISyntaxException;
}
