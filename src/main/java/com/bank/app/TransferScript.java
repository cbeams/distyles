package com.bank.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bank.config.TransferConfig;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.service.TransferService;

public class TransferScript {

	public static void main(String... args) throws InsufficientFundsException {

		ApplicationContext ctx =
//			new ClassPathXmlApplicationContext("/com/bank/config/transfer-xml-config.xml");
//			new ClassPathXmlApplicationContext("/com/bank/config/transfer-annotation-config.xml");
			new AnnotationConfigApplicationContext(TransferConfig.class);
		

		TransferService transferService = ctx.getBean(TransferService.class);

		TransferReceipt receipt = transferService.transfer(10.00, "A123", "C456");

		System.out.println(receipt);

	}

}
