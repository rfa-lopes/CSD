package csd.wallet.Models;

public class SmartContract {

    long ownerId;
    String code;

    public SmartContract() { }

    public SmartContract( long ownerId, String code) {
        this.ownerId = ownerId;
        this.code = code;
    }

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
