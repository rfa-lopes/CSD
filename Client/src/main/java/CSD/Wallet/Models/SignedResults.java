package CSD.Wallet.Models;

import java.io.Serializable;
import java.util.Map;

public class SignedResults implements Serializable {

    Map<Integer, byte[]> signatureReceive;

    byte[] result;

    public SignedResults() { }

    public SignedResults(Map<Integer, byte[]> signatureReceive, byte[] result) {
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
