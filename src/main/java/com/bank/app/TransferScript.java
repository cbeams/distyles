package com.bank.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.service.TransferService;

public class TransferScript {

	public static void main(String... args) throws InsufficientFundsException {

		ApplicationContext ctx =
			new ClassPathXmlApplicationContext("/com/bank/config/transfer-service-config.xml");
			//new AnnotationConfigApplicationContext(TransferConfig.class);
		

		TransferService transferService = (TransferService) ctx.getBean("transferService");

		TransferReceipt receipt = transferService.transfer(10.00, "A123", "C456");

		System.out.println(receipt);

	}

}
