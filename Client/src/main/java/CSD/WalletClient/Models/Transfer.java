package CSD.WalletClient.Models;

import javax.validation.constraints.NotNull;

public class TransferModel1 {

    public class Transfer {

        public static final String FORMAT_DATE = "yyyy/MM/dd HH:mm:ss";

        public Transfer(){}

        public Transfer(long fromId, long toId, long amount){
            this.fromId = fromId;
            this.toId = toId;
            this.amount = amount;
            this.date = getActualDate();
        }


        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        long id;

        @NotNull
        long fromId;

        @NotNull
        long toId;

        @NotNull
        long amount;

        @NotNull
        String date;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public void setTo(long toId) {
            this.toId = toId;
        }

        public long getAmount() {
            return amount;
        }

        public void setAmount(long amount) {
            this.amount = amount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    public String getInfo() {
        return  String.format("\n",
                "FromID: %l\n" + "ToID: %s\n" + "Amount: %l\n",
                fromId, toId, amount );
    }
}
