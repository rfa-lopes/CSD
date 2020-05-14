package csd.wallet.Replication;

import java.util.Map;

public class SignsResults {

    Map<Integer, byte[]> signatureReceive;

    byte[] result;

    public SignsResults(Map<Integer, byte[]> signatureReceive, byte[] result) {
        this.signatureReceive = signatureReceive;
        this.result = result;
    }

    public Map<Integer, byte[]> getSignatureReceive() {
        return signatureReceive;
    }

    public void setSignatureReceive(Map<Integer, byte[]> signatureReceive) {
        this.signatureReceive = signatureReceive;
    }

    public byte[] getResult() {
        return result;
    }

    public void setResult(byte[] result) {
        this.result = result;
    }
}
