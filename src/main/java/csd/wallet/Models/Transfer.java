package csd.wallet.Models;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
public class Transfer {

    public static final String FORMAT_DATE = "yyyy/MM/dd HH:mm:ss";

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

    public void setDate() {
        this.date = new SimpleDateFormat(FORMAT_DATE).format(new Date());
    }

}