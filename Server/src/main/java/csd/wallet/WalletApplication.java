package csd.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletApplication {

	public static void main(String[] args) {
		
		System.setProperty("java.security.policy","SmartContract/SC.policy");
		
		System.setSecurityManager(new SecurityManager());
		
		SpringApplication.run(WalletApplication.class, args);
	}

}
