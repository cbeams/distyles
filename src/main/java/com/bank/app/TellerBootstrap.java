package com.bank.app;

import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TellerBootstrap {

	public static void main(String... args) throws IOException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("com/bank/app/app-config.xml");
		TellerUI tellerUI = (TellerUI) ctx.getBean("tellerUI");
		tellerUI.start();
	}

}
