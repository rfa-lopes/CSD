package CSD.Wallet.Utils;

import java.io.Serializable;

public interface Result<T> extends Serializable {

    enum ErrorCode {OK, BAD_REQUEST, NOT_FOUND, INTERNAL_ERROR, NOT_IMPLEMENTED, TIME_OUT}

    boolean isOK();

    T value();

    ErrorCode error();

    static <T> Result<T> ok(T result) {
        return new OkResult<>(result);
    }

    static <T> OkResult<T> ok() {
        return new OkResult<>(null);
    }

    static <T> ErrorResult<T> error(ErrorCode error) {
        return new ErrorResult<>(error);
    }

}

class OkResult<T> implements Result<T> {

    final T result;

    OkResult(T result) {
        this.result = result;
    }

    @Override
    public boolean isOK() {
        return true;
    }

    @Override
    public T value() {
        return result;
    }

    @Override
    public Result.ErrorCode error() {
        return Result.ErrorCode.OK;
    }

}

class ErrorResult<T> implements Result<T> {

    final Result.ErrorCode error;

    ErrorResult(Result.ErrorCode error) {
        this.error = error;
    }

    @Override
    public boolean isOK() {
        return false;
    }

    @Override
    public T value() {
        throw new RuntimeException("Attempting to extract the value of an Error: " + error());
    }

    @Override
    public Result.ErrorCode error() {
        return error;
    }
}