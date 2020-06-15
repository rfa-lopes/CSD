package CSD.Wallet.Models;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    public Wallet() {
    }

    public Wallet(@NotNull String name) {
        this.name = name;
        this.amount_add = "0";
    }

    long id;

    @NotNull
    String name;

    @NotNull
    String amount_add;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(String amount_add) {
        this.amount_add = amount_add;
    }

    public String getInfo() {
       return String.format("\n"+ "ID: %s\n" + "Name owner: %s\n" + "Amount: %s\n", (id), name, amount_add);
    }
}



