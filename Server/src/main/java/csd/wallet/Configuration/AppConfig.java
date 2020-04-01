package csd.wallet.Configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import bftsmart.tom.ServiceProxy;


@Configuration
public class AppConfig {

	@Bean
	public ServiceProxy serviceProxy(@Value("${server.port}") int serverport) {
		return new ServiceProxy(serverport % 4);
	}

}
