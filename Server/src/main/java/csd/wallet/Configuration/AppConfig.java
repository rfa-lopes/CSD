package csd.wallet.Configuration;

import bftsmart.tom.AsynchServiceProxy;
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
	public AsynchServiceProxy asynchServiceProxy(@Value("${bftsmart.id}") int id) {
		return new AsynchServiceProxy(id);
	}

	@Bean
	public BFTServiceProxy bftServiceProxy() {
		return new BFTServiceProxy();
	}

}
