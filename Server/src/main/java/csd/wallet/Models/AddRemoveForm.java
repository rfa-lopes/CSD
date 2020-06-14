package csd.wallet.Models;

import java.io.Serializable;
import java.math.BigInteger;

public class AddRemoveForm implements Serializable {

    public AddRemoveForm(){}

    long id;

    BigInteger amount_add;

    long amount_ope;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigInteger getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(BigInteger amount_add) {
        this.amount_add = amount_add;
    }

    public long getAmount_ope() {
        return amount_ope;
    }

    public void setAmount_ope(long amount_ope) {
        this.amount_ope = amount_ope;
    }
}
