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
    long amount_ope;

    @NotNull
    String operation;

    public Deposits() {
    }

    public Deposits(long walletId, String amount_add, long amount_ope, Operation operation) {
        this.walletId = walletId;
        this.amount_add = amount_add;
        this.amount_ope = amount_ope;
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

    public long getAmount_ope() {
        return amount_ope;
    }

    public void setAmount_ope(long amount_ope) {
        this.amount_ope = amount_ope;
    }
}
