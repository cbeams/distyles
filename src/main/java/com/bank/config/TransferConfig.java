package com.bank.config;

import org.springframework.context.annotation.Bean;

import com.bank.repository.AccountRepository;
import com.bank.repository.internal.SimpleAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.ZeroFeePolicy;

public class TransferConfig {
	
//	@Value("${flatfee.amount}")
//	private double feeAmount;
	
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
		return new ZeroFeePolicy();
//		return new FlatFeePolicy(feeAmount);
	}
	
}
