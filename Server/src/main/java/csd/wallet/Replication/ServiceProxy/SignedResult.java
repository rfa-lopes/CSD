package csd.wallet.Replication.ServiceProxy;

import csd.wallet.Replication.Result;

public class SignedResult {

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
