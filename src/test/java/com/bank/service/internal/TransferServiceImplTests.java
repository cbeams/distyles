package com.bank.service.internal;


import static com.bank.repository.internal.InMemoryAccountRepository.Data.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferConfirmation;
import com.bank.repository.AccountNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.internal.InMemoryAccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;
import com.bank.service.internal.FlatFeePolicy;
import com.bank.service.internal.TransferServiceImpl;
import com.bank.service.internal.ZeroFeePolicy;

public class TransferServiceImplTests {
	private AccountRepository accountRepository;
	private TransferService transferService;
	
	@Before
	public void setUp() {
		accountRepository = new InMemoryAccountRepository();
		FeePolicy feePolicy = new ZeroFeePolicy();
		transferService = new TransferServiceImpl(accountRepository, feePolicy);
		
		assertThat(accountRepository.findById(A123_ID).getBalance(), equalTo(A123_INITIAL_BAL));
		assertThat(accountRepository.findById(C456_ID).getBalance(), equalTo(C456_INITIAL_BAL));
	}

	@Test
	public void testTransfer() throws InsufficientFundsException {
		double transferAmount = 100.00;
		
		TransferConfirmation confirmation = transferService.transfer(transferAmount, A123_ID, C456_ID);
		
		assertThat(confirmation.getTransferAmount(), equalTo(transferAmount));
		assertThat(confirmation.getSourceAccountBalance(), equalTo(A123_INITIAL_BAL-transferAmount));
		assertThat(confirmation.getDestinationAccountBalance(), equalTo(C456_INITIAL_BAL+transferAmount));
		
		assertThat(accountRepository.findById(A123_ID).getBalance(), equalTo(A123_INITIAL_BAL-transferAmount));
		assertThat(accountRepository.findById(C456_ID).getBalance(), equalTo(C456_INITIAL_BAL+transferAmount));
	}
	
	@Test
	public void testInsufficientFunds() {
		double overage = 9.00;
		double transferAmount = A123_INITIAL_BAL + overage;
		
		try {
			transferService.transfer(transferAmount, A123_ID, C456_ID);
			fail("expected InsufficientFundsException");
		} catch (InsufficientFundsException ex) {
			assertThat(ex.getTargetAccountId(), equalTo(A123_ID));
			assertThat(ex.getOverage(), equalTo(overage));
		}
		
		assertThat(accountRepository.findById(A123_ID).getBalance(), equalTo(A123_INITIAL_BAL));
		assertThat(accountRepository.findById(C456_ID).getBalance(), equalTo(C456_INITIAL_BAL));
	}
	
	@Test
	public void testNonExistentSourceAccount() throws InsufficientFundsException {
		try {
			transferService.transfer(1.00, Z999_ID, C456_ID);
			fail("expected AccountNotFoundException");
		} catch (AccountNotFoundException ex) { }
		
		assertThat(accountRepository.findById(C456_ID).getBalance(), equalTo(C456_INITIAL_BAL));
	}
	
	@Test
	public void testNonExistentDestinationAccount() throws InsufficientFundsException {
		try {
			transferService.transfer(1.00, A123_ID, Z999_ID);
			fail("expected AccountNotFoundException");
		} catch (AccountNotFoundException ex) { }
		
		assertThat(accountRepository.findById(A123_ID).getBalance(), equalTo(A123_INITIAL_BAL));
	}
	
	@Test
	public void testZeroTransferAmount() throws InsufficientFundsException {
		try {
			transferService.transfer(0.00, A123_ID, C456_ID);
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) { }
	}
	
	@Test
	public void testNegativeTransferAmount() throws InsufficientFundsException {
		try {
			transferService.transfer(-100.00, A123_ID, C456_ID);
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) { }
	}
	
	@Test
	public void testTransferAmountLessThanOneCent() throws InsufficientFundsException {
		try {
			transferService.transfer(0.009, A123_ID, C456_ID);
			fail("expected IllegalArgumentException");
		} catch (IllegalArgumentException ex) { }
	}

	@Test
	public void testCustomizedMinimumTransferAmount() throws InsufficientFundsException {
		transferService.transfer(1.00, A123_ID, C456_ID); // should be fine
		transferService.setMinimumTransferAmount(10.00);
		transferService.transfer(10.00, A123_ID, C456_ID); // fine against new minimum
		try {
			transferService.transfer(9.00, A123_ID, C456_ID); // violates new minimum!
			fail("expected IllegalArgumentException on 9.00 transfer that violates 10.00 minimum");
		} catch (IllegalArgumentException ex) { }
	}
	
	@Test
	public void testNonZeroFeePolicy() throws InsufficientFundsException {
		double flatFee = 5.00;
		double transferAmount = 10.00;
		transferService = new TransferServiceImpl(accountRepository, new FlatFeePolicy(flatFee));
		transferService.transfer(transferAmount, A123_ID, C456_ID);
		assertThat(accountRepository.findById(A123_ID).getBalance(), equalTo(A123_INITIAL_BAL-transferAmount-flatFee));
		assertThat(accountRepository.findById(C456_ID).getBalance(), equalTo(C456_INITIAL_BAL+transferAmount));
	}
	
}
