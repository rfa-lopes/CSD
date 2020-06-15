package CSD.Wallet.Models;

public class AddRemoveForm {

    public AddRemoveForm() {
    }

    long id;

    String amount_add;

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
}