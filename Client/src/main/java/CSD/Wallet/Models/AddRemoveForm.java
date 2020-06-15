package CSD.Wallet.Models;

import java.math.BigInteger;

public class AddRemoveForm {

    public AddRemoveForm() {
    }

    long id;

    long amount_add;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(long amount_add) {
        this.amount_add = amount_add;
    }
}