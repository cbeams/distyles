package com.bank.app;

import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bank.config.AppConfig;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.service.TransferService;

public class TransferScript {

	public static void main(String... args) throws InsufficientFundsException {

		ApplicationContext ctx =
			new AnnotationConfigApplicationContext(AppConfig.class);


		TransferService transferService = ctx.getBean(TransferService.class);

		// generate a random amount between 10.00 and 90.00 dollars
		double amount = (new Random().nextInt(8) + 1) * 10;

		TransferReceipt receipt = transferService.transfer(amount, "A123", "C456");

		System.out.println(receipt);

	}

}
