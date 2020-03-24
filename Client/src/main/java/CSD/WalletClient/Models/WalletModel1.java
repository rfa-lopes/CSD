package CSD.WalletClient.Models;

public class WalletModel1 {

    long id;
    String nameOwner;
    long amount;

    public WalletModel1(String nameOwner) {
        this.id = 0;
        this.nameOwner = nameOwner;
        this.amount = 0;
    }

    public long getAmount() {
        return amount;
    }

    public String getInfo() {
        return  String.format("\n",
                "ID: %l\n" + "Name owner: %s\n" + "Amount: %l\n",
                id, nameOwner, amount );
    }


}
