package CSD.Wallet.Models;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Transfer {

    public static final String FORMAT_DATE = "yyyy/MM/dd HH:mm:ss";

    long id;

    @NotNull
    long fromId;

    @NotNull
    long toId;

    @NotNull
    String amount_add;

    @NotNull
    long amount_ope;

    @NotNull
    String date;

    public Transfer() {
    }

    public Transfer(long fromId, long toId, String amount_add, long amount_ope) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount_add = amount_add;
        this.amount_ope = amount_ope;
    }

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

    public String getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(String amount_add) {
        this.amount_add = amount_add;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getActualDate() {
        return new SimpleDateFormat(FORMAT_DATE).format(new Date());
    }

    public String getNextDay() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        return new SimpleDateFormat(FORMAT_DATE).format(dt);
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public long getAmount_ope() {
        return amount_ope;
    }

    public void setAmount_ope(long amount_ope) {
        this.amount_ope = amount_ope;
    }

    public String getInfo() {
        return String.format("\n" + "FromID: %s\n" + "ToID: %s\n" + "Amount: %s\n", fromId, toId, amount_add);
    }
}
