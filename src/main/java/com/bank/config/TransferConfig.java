package com.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

import com.bank.repository.AccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.FlatFeePolicy;

@Configuration
@Import(InfrastructureConfig.class)
public class TransferConfig {
	
	@Value("${flatfee.amount}")
	private double feeAmount;
	
	@Bean
	public TransferService transferService() {
		return new DefaultTransferService(accountRepository(), feePolicy());
	}

	@Bean
	public AccountRepository accountRepository() {
		return new SimpleAccountRepository();
	}

	@Bean
	public FeePolicy feePolicy() {
		return new FlatFeePolicy(feeAmount);
	}
	
}
