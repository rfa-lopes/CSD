package csd.wallet.Configuration;

import csd.wallet.Replication.ServiceProxy.BFTServiceProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import bftsmart.tom.ServiceProxy;

@Configuration
public class AppConfig {

	@Bean
	public ServiceProxy serviceProxy(@Value("${bftsmart.id}") int id) {
		return new ServiceProxy(id);
	}

	@Bean
	public BFTServiceProxy bftServiceProxy(@Value("${bftsmart.id}") int id) {
		return new BFTServiceProxy(id);
	}

}
