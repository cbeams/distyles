package com.bank.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.bank.repository.AccountRepository;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import com.bank.service.internal.FlatFeePolicy;
import com.bank.service.internal.TransferServiceImpl;

@Configuration
@ImportResource("classpath:com/bank/app/infrastructure-config.xml")
public class AppConfig {
	
	@Autowired DataSource dataSource;
	@Value("${flatfee.amount}") double feeAmount;
	
	@Bean
	public TransferService transferService() {
		return new TransferServiceImpl(accountRepository(), feePolicy());
	}

	@Bean
	public AccountRepository accountRepository() {
		return new JdbcAccountRepository(dataSource);
	}

	@Bean
	public FeePolicy feePolicy() {
		return new FlatFeePolicy(feeAmount);
	}
	
}
