package csd.wallet.Configuration;

import bftsmart.tom.AsynchServiceProxy;
import csd.wallet.Replication.ServiceProxy.BFTServiceProxy;
import csd.wallet.WebFilters.AuthenticatorFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import bftsmart.tom.ServiceProxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.servlet.Filter;

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

	private @Autowired
	AutowireCapableBeanFactory beanFactory;

	@Bean
	public FilterRegistrationBean authFilter(){
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();

		Filter authFilter = new AuthenticatorFilter();
		beanFactory.autowireBean(authFilter);

		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns(
				"/wallets/*",
				"/smartcontract/*",
				"/transfers/*");

		return registrationBean;
	}

}
