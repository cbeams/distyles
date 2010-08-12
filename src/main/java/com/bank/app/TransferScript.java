package com.bank.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.service.TransferService;

public class TransferScript {

	public static void main(String... args) throws InsufficientFundsException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/com/bank/app/transfer-config.xml");
		
		TransferService transferService = ctx.getBean(TransferService.class);
	
		TransferReceipt confirmation = transferService.transfer(10.00, "A123", "C456");
		
		System.out.println(confirmation);
	}
	
}
