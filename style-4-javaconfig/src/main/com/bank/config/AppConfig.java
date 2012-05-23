package com.bank.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.FlatFeePolicy;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/bank/config/app.properties")
public class AppConfig {

	@Autowired
	private Environment env;

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:com/bank/config/schema.sql")
			.addScript("classpath:com/bank/config/seed-data.sql")
			.build();
	}

	@Bean
	public FeePolicy feePolicy() {
		return new FlatFeePolicy(env.getProperty("flatfee.amount", double.class));
	}

	@Bean
	public AccountRepository accountRepository() {
		return new JdbcAccountRepository(dataSource());
	}

	@Bean
	public TransferService transferService() {
		DefaultTransferService transferService =
				new DefaultTransferService(accountRepository(), feePolicy());
		transferService.setMinimumTransferAmount(
				env.getProperty("minimum.transfer.amount", double.class));
		return transferService;
	}

}
