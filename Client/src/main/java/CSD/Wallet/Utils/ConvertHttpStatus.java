package CSD.Wallet.Utils;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public class ConvertHttpStatus {
    public static HttpStatus convert(String errorCode){
        switch (errorCode){
            case "OK": return OK;
            case "BAD_REQUEST": return BAD_REQUEST;
            case "NOT_FOUND": return NOT_FOUND;
            case "NOT_IMPLEMENTED": return NOT_IMPLEMENTED;
            case "TIME_OUT": return REQUEST_TIMEOUT;
            default: return INTERNAL_SERVER_ERROR;
        }

    }

}
