package csd.wallet.Replication.ServiceProxy;

import csd.wallet.Replication.Operations.Result;

import java.io.Serializable;

public class SignedResult implements Serializable {

    private Result result;
    private byte[] signature;
    private int id;

    public SignedResult(Result result, byte[] signature, int id){
        this.result = result;
        this.signature = signature;
        this.id = id;
    }

    public Result getResult() {
        return result;
    }

    public byte[] getSignature(){
        return signature;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
