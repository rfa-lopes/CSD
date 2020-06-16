package csd.wallet.Replication;

import csd.wallet.Replication.Operations.Result;

import java.io.Serializable;
import java.util.Map;

public class SignedResults implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8244957475689532671L;

	Map<Integer, byte[]> signatureReceive;

    Result result;

    public SignedResults() {
    }

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
