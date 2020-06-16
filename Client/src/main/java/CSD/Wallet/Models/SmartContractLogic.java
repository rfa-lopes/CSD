package CSD.Wallet.Models;

public class SmartContractLogic implements SmartContractI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int kek;

	public SmartContractLogic() {
		this.kek = 0;
	}

	@Override
	public void execute() {
		kek++;
		System.out.println("Value of kek is:" + kek);

	}

}
