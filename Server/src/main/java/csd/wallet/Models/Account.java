package csd.wallet.Models;

import csd.wallet.Utils.HashingUtils;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @NotNull
    String username;

    @NotNull
    String password;

    public Account() {
    }

    public Account(@NotNull long id, @NotNull String password) {
        this.id = id;
        this.password = HashingUtils.hashPwd(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void generatePasswordHash(){
        this.password =  HashingUtils.hashPwd(password);
    }

    public boolean isValidPassword(String toCompare) {
        return HashingUtils.validatePassword(toCompare, password);
    }

}
