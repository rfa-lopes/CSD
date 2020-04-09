package csd.wallet.Models;

import java.io.Serializable;

public class ResponseWrapper implements Serializable {
    Object value;
    Exception exception;

    public ResponseWrapper(){}

    public ResponseWrapper(Object value, Exception exception ){
        this.value = value;
        this.exception = exception;
    }

    public Object isOk() throws Exception {
        if(exception != null)
            throw exception;
        else return value;

    }

    public Object getValue() {
        return value;
    }


    public void setValue(Object value) {
        this.value = value;
    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

}
