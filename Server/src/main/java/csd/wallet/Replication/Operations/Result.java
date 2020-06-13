package csd.wallet.Replication.Operations;

import lombok.Data;

import java.io.Serializable;

public interface Result<T> extends Serializable {

    enum ErrorCode {OK, BAD_REQUEST, NOT_FOUND, INTERNAL_ERROR, NOT_IMPLEMENTED, TIME_OUT, CONFLICT}

    T getResult();

    boolean isOK();

    ErrorCode getError();

    static <T> Result<T> ok(T result) {
        return new OkResult<>(result);
    }

    static <T> OkResult<T> ok() {
        return new OkResult<>(null);
    }

    static <T> ErrorResult<T> getError(ErrorCode error) {
        return new ErrorResult<>(error);
    }

}

@Data
class OkResult<T> implements Result<T>, Serializable {

    final T result;

    OkResult(T result) {
        this.result = result;
    }

    @Override
    public boolean isOK() {
        return true;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public ErrorCode getError() {
        return ErrorCode.OK;
    }

}

@Data
class ErrorResult<T> implements Result<T>, Serializable {

    final ErrorCode error;

    ErrorResult(ErrorCode error) {
        this.error = error;
    }

    @Override
    public boolean isOK() {
        return false;
    }

    @Override
    public T getResult() {
        return null;
    }

    @Override
    public ErrorCode getError() {
        return error;
    }
}