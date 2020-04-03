package csd.wallet.Models;

import java.io.Serializable;

public class AddRemoveForm implements Serializable {

    public AddRemoveForm(){}

    long id;

    long amount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}
