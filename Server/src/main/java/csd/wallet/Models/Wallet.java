package csd.wallet.Models;

import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    String name;

    @NotNull
    BigInteger amount_add;

    public Wallet() {
    }

    public Wallet(@NotNull String name) {
        this.name = name;
        this.amount_add = BigInteger.ZERO;
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

    public BigInteger getAmount_add() {
        return amount_add;
    }

    public void setAmount_add(BigInteger amount_add) {
        this.amount_add = amount_add;
    }
}
