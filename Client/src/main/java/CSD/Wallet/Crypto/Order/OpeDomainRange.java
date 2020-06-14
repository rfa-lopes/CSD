/*
 * Domain Range Classs used in OPE
 * Verf 17/2/2017
 */

package CSD.Wallet.Crypto.Order;

public class OpeDomainRange {
	public long d;
	public long rLow;
	public long rHigh;
	
	public OpeDomainRange(long d, long rLow, long rHigh){
		this.d = d;
		this.rLow = rLow;
		this.rHigh = rHigh;
	}
}
