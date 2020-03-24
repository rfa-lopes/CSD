package CSD.WalletClient.Models;

public class TransferModel1 {

    long fromId;
    long toId;
    long amount;

    public TransferModel1(long fromId, long toId, long amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getInfo() {
        return  String.format("\n",
                "FromID: %l\n" + "ToID: %s\n" + "Amount: %l\n",
                fromId, toId, amount );
    }
}
