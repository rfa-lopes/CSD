package CSD.Wallet.Commands.Authentication;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.net.URISyntaxException;

@ShellComponent
public interface AuthCommandInter {
    /**
     * Description
     * Saves user's credentials
     * @param username User's id
     * @param password Wallet's password
     * @return Status code of operation
     */
    String login(String username, String password) throws URISyntaxException;


    String createUser(String username, String password) throws URISyntaxException;

}
