package CSD.Wallet.Models;

import javax.validation.constraints.NotNull;

public class Account {

    @NotNull
    String username;
    @NotNull
    String password;

    public Account() {
    }

    public Account(@NotNull String username, @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
