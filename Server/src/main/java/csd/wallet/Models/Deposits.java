package csd.wallet.Models;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Deposits {

    public enum Operation {
        ADD, REMOVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    long walletId;

    @NotNull
    @Lob
    @Column(length = 3000)
    String amount_add;

    @NotNull
    String amount_ope_rnd;

    @NotNull
    String operation;

    public Deposits() {
    }

    public Deposits(long walletId, String amount_add, String amount_ope_rnd, Operation operation) {
        this.walletId = walletId;
        this.amount_add = amount_add;
        this.amount_ope_rnd = amount_ope_rnd;
        this.operation = operation.name();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWalletId() {
        return walletId;
    }

    public void setWalletId(long walletId) {
        this.walletId = walletId;
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
