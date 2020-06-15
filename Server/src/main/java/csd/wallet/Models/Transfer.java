package csd.wallet.Models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class Transfer implements Serializable {

    public static final String FORMAT_DATE = "yyyy/MM/dd HH:mm:ss";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    long fromId;

    @NotNull
    long toId;

    @NotNull
    @Lob
    @Column(length = 5000)
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
        this.date = getActualDate();
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

    public void setToId(long toId) {
        this.toId = toId;
    }

    public String getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(String amount_add) {
        this.amount_add = amount_add;
    }

    public long getAmount_ope() {
        return amount_ope;
    }

    public void setAmount_ope(long amount_ope) {
        this.amount_ope = amount_ope;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String getActualDate() {
        return new SimpleDateFormat(FORMAT_DATE).format(new Date());
    }

}