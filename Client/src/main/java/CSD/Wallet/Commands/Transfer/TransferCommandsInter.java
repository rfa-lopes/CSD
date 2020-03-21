package CSD.Wallet.Commands.Transfer;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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



}
