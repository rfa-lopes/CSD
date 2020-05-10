package CSD.Wallet.Models;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;

public class SmartContract implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    long ownerId;

    @NotNull
    String code;

    public SmartContract(@NotNull long ownerId, @NotNull String code) {
        this.ownerId = ownerId;
        this.code = code;
    }

    public SmartContract() { }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
