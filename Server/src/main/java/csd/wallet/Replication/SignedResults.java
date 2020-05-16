package csd.wallet.Replication;

import java.io.Serializable;
import java.util.Map;

public class SignedResults implements Serializable {

    Map<Integer, byte[]> signatureReceive;

    Result result;

    public SignedResults(Map<Integer, byte[]> signatureReceive, Result result) {
        this.signatureReceive = signatureReceive;
        this.result = result;
    }

    public Map<Integer, byte[]> getSignatureReceive() {
        return signatureReceive;
    }

    public void setSignatureReceive(Map<Integer, byte[]> signatureReceive) {
        this.signatureReceive = signatureReceive;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
