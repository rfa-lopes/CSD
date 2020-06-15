package CSD.Wallet.Models;

import java.io.Serializable;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;

public class StringWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    String value;

    public StringWrapper() {
    }

    public StringWrapper(@NotNull String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}



