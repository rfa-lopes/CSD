package CSD.Wallet.Utils;

import java.io.Serializable;

public class Result<T> implements Serializable {

    T result;

    String error;

    boolean ok;

    public Result() { }

    public Result(T result, String error, boolean ok) {
        this.result = result;
        this.error = error;
        this.ok = ok;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}