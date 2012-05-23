package com.bank.app;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.TransferService;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.FlatFeePolicy;

public class TransferScript {

	public static void main(String... args)
			throws InsufficientFundsException, IOException {

		DataSource dataSource = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("classpath:com/bank/config/schema.sql")
				.addScript("classpath:com/bank/config/seed-data.sql").build();

		Properties props = new Properties();
		props.load(TransferScript.class.getClassLoader()
				.getResourceAsStream("com/bank/config/app.properties"));

		TransferService transferService = new DefaultTransferService(
				new JdbcAccountRepository(dataSource),
				new FlatFeePolicy(Double.valueOf(props.getProperty("flatfee.amount"))));

		transferService.setMinimumTransferAmount(
				Double.valueOf(props.getProperty("minimum.transfer.amount")));

		// generate a random amount between 10.00 and 90.00 dollars
		double amount = (new Random().nextInt(8) + 1) * 10;

		TransferReceipt reciept = transferService.transfer(amount, "A123", "C456");

		System.out.println(reciept);
	}

}
