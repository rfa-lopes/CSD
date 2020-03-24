package CSD.WalletClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletClientApplication {
	
	private static final String TRUST_STORE = "src/main/resources/client.jks";
	public static void main(String[] args) {
		
		System.setProperty("javax.net.ssl.trustStore",TRUST_STORE); 
	    System.setProperty("javax.net.ssl.trustStorePassword","client");
	      
		SpringApplication.run(WalletClientApplication.class, args);
	}

}
