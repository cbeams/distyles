package com.bank.service;

import com.bank.domain.InsufficientFundsException;
import com.bank.domain.TransferReceipt;

public interface TransferService {

	TransferReceipt transfer(double amount, String srcAcctId, String destAcctId)
		throws InsufficientFundsException;

	void setMinimumTransferAmount(double minimumTransferAmount);

}
