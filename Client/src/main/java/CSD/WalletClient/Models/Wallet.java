package CSD.WalletClient.Models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    public Wallet() {
    }

    public Wallet(@NotNull String name) {
        this.name = name;
        this.amount = 0;
    }

    long id;

    @NotNull
    String name;

    @NotNull
    long amount;

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

    public long getAmount() {
        return amount;
    }

    public void setAmount(long value) {
        this.amount = value;
    }

    public void addAmount(long value) {
        this.amount += value;
    }

    public void removeAmount(long value) {
        this.amount -= value;
    }

    public String getInfo() {
       return String.format(
                "\n"+
                "ID: %s\n" + "Name owner: %s\n" + "Amount: %s\n",
                Long.toString(getId()), getName(),  Long.toString(getAmount()));
    }
}



