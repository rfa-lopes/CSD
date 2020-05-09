package csd.wallet.Models;

import java.io.File;

public class SmartContract {

    long ownerId;
    File code;

    public SmartContract(long ownerId, File code) {
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

    public File getCode() {
        return code;
    }

    public void setCode(File code) {
        this.code = code;
    }
}
