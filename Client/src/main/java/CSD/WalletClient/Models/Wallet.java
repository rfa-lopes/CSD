package CSD.WalletClient.Models;

public class Wallet{

    private long id;
    private String name;
    private long amount;

    public Wallet() {
    }

    public Wallet(String name) {
        this.name = name;
        this.amount = 0;

    }

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

    public String getInfo() {
        return String.format("\n",
                "ID: %l\n" + "Name owner: %s\n" + "Amount: %l\n",
                id, name, amount);
    }

}

