package com.bank.service.internal;

import static java.lang.String.format;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

import com.bank.domain.Account;
import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferConfirmation;
import com.bank.repository.AccountRepository;
import com.bank.service.FeePolicy;
import com.bank.service.TransferService;


@Named
public class TransferServiceImpl implements TransferService {
	
	private final AccountRepository accountRepository;
	private final FeePolicy feePolicy;
	private double minimumTransferAmount = 1.00;
	
	@Inject
	public TransferServiceImpl(AccountRepository accountRepository, FeePolicy feePolicy) {
		this.accountRepository = accountRepository;
		this.feePolicy = feePolicy;
	}

	@Override
	@TransactionAttribute
	public TransferConfirmation transfer(double amount, String srcAcctId, String dstAcctId) throws InsufficientFundsException {
		if (amount < minimumTransferAmount)
			throw new IllegalArgumentException(
					format("transfer amount must be at least $%.2f USD", minimumTransferAmount));
		
		Account srcAcct = accountRepository.findById(srcAcctId);
		Account dstAcct = accountRepository.findById(dstAcctId);
		
		double fee = feePolicy.calculateFee(amount);
		if (fee > 0)
			srcAcct.debit(fee);
		
		srcAcct.debit(amount);
		dstAcct.credit(amount);
		
		accountRepository.updateBalance(srcAcct);
		accountRepository.updateBalance(dstAcct);

		return new TransferConfirmation(amount, fee, srcAcct, dstAcct);
	}

	@Override
	public void setMinimumTransferAmount(double minimumTransferAmount) {
		this.minimumTransferAmount = minimumTransferAmount;
	}
}
