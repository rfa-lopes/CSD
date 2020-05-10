package CSD.Wallet.Models;

import java.io.Serializable;

public class SmartContract implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long ownerId;
    String code;

    public SmartContract(long ownerId, String code) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
