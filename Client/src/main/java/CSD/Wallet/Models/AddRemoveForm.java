package CSD.Wallet.Models;

public class AddRemoveForm {

    public AddRemoveForm() {
    }

    long id;

    String amount_add;

    String amount_ope_rnd;

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

    public String getAmount_ope_rnd() {
        return amount_ope_rnd;
    }

    public void setAmount_ope_rnd(String amount_ope_rnd) {
        this.amount_ope_rnd = amount_ope_rnd;
    }
}