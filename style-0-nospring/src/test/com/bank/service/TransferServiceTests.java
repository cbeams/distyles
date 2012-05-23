package com.bank.service;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;
import com.bank.repository.internal.JdbcAccountRepository;
import com.bank.service.internal.DefaultTransferService;
import com.bank.service.internal.FlatFeePolicy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Integration tests for {@link TransferService}.
 */
public class TransferServiceTests {

	private JdbcTemplate jdbcTemplate;

	private TransferService transferService;

	@Before
	public void setUp() throws IOException {
		DataSource dataSource = new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.HSQL)
			.addScript("classpath:com/bank/config/schema.sql")
			.addScript("classpath:com/bank/config/seed-data.sql")
			.build();

		this.jdbcTemplate = new JdbcTemplate(dataSource);

		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("com/bank/config/app.properties"));

		this.transferService = new DefaultTransferService(
				new JdbcAccountRepository(dataSource),
				new FlatFeePolicy(Double.valueOf(props.getProperty("flatfee.amount"))));

		this.transferService.setMinimumTransferAmount(
				Double.valueOf(props.getProperty("minimum.transfer.amount")));
	}

	@Ignore @Test
	public void transferServiceIsTransactional() {
		boolean isTxProxy = false;

		if (AopUtils.isAopProxy(transferService)) {
			for (Advisor advisor : ((Advised)transferService).getAdvisors()) {
				if (TransactionInterceptor.class.equals(advisor.getAdvice().getClass())) {
					isTxProxy = true;
				}
			}
		}

		assertTrue("transferService does not have transactional advice applied", isTxProxy);
	}

	@Test
	public void transfer10Dollars() throws InsufficientFundsException {
		assertThat(queryForBalance("A123"), equalTo(100.00));
		final double transferAmount = 10.00;
		TransferReceipt receipt = transferService.transfer(transferAmount, "A123", "C456");
		double transferTotal =  transferAmount + receipt.getFeeAmount();
		assertThat(queryForBalance("A123"), equalTo(100.00 - transferTotal));
	}

	private double queryForBalance(String acctNumber) {
		return jdbcTemplate.queryForObject(
				"select balance from account where id = ?", Double.class, acctNumber);
	}
}
