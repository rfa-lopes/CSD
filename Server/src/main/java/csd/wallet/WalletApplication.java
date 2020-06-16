package csd.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		
		System.setProperty("java.security.policy","SmartContract/SC.policy");
		
		java.security.Policy.setPolicy(new net.sourceforge.prograde.policy.ProGradePolicy());

		
		SpringApplication.run(WalletApplication.class, args);
	}

}
