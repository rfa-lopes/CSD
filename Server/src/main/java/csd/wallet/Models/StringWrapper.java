package csd.wallet.Models;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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



