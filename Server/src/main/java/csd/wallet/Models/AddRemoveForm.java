package csd.wallet.Models;

import java.io.Serializable;

public class AddRemoveForm implements Serializable {

    public AddRemoveForm() {
    }

    long id;

    String amount_add;

    //long amount_ope;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(String amount_add) {
        this.amount_add = amount_add;
    }

    //public long getAmount_ope() {
    //  return amount_ope;
    //}

    //public void setAmount_ope(long amount_ope) {
    //    this.amount_ope = amount_ope;
    //}
}
