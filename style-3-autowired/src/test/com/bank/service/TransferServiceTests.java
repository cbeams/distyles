package com.bank.service;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Integration tests for {@link TransferService}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:com/bank/config/app-config.xml")
public class TransferServiceTests {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private TransferService transferService;

	@Autowired
	void init(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Test
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
