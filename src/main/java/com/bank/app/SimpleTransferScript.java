package com.bank.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bank.domain.InsufficientFundsException;
import com.bank.repository.AccountRepository;
import com.bank.service.TransferService;

public class SimpleTransferScript {

	public static void main(String... args) throws InsufficientFundsException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/com/bank/app/app-config.xml");
		
		TransferService transferService = ctx.getBean(TransferService.class);
		AccountRepository accountRepository = ctx.getBean(AccountRepository.class);
	
		printBalance("A123", accountRepository);
		printBalance("C456", accountRepository);
		
		System.out.println("transfering 10.00€ from account A123 to C456");
		transferService.transfer(10.00, "A123", "C456");
		
		printBalance("A123", accountRepository);
		printBalance("C456", accountRepository);
	}
	
	private static void printBalance(String acctId, AccountRepository accountRepository) {
		System.out.printf("balance for account %s: %.02f€\n", acctId, accountRepository.findById(acctId).getBalance());
	}

}
