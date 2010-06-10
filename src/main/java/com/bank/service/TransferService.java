package com.bank.service;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferConfirmation;

public interface TransferService {

	TransferConfirmation transfer(double amount, String srcAcctId, String destAcctId)
		throws InsufficientFundsException;

	void setMinimumTransferAmount(double minimumTransferAmount);

}
