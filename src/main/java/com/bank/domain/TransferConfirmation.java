package com.bank.domain;

import static java.lang.String.format;

public class TransferConfirmation {

	private final double transferAmount;
	private final double feeAmount;
	private final Account dstAcct;
	private final Account srcAcct;

	public TransferConfirmation(double amount, double feeAmount, Account srcAcct, Account dstAcct) {
		this.transferAmount = amount;
		this.feeAmount = feeAmount;
		this.srcAcct = Account.copy(srcAcct);
		this.dstAcct = Account.copy(dstAcct);
	}

	public double getTransferAmount() {
		return transferAmount;
	}
	
	public double getFeeAmount() {
		return feeAmount;
	}
	
	public String getSourceAccountId() {
		return srcAcct.getId();
	}
	
	public String getDestinationAccountId() {
		return dstAcct.getId();
	}

	public double getSourceAccountBalance() {
		return srcAcct.getBalance();
	}

	public double getDestinationAccountBalance() {
		return dstAcct.getBalance();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
			.append(format("Successfully transferred $%.2f from account %s to %s\n",
					getTransferAmount(), getSourceAccountId(), getDestinationAccountId()))
			.append(format("\tnew balance for account %s: $%.2f\n",
					getSourceAccountId(), getSourceAccountBalance()))
			.append(format("\tnew balance for account %s: $%.2f\n",
					getDestinationAccountId(), getDestinationAccountBalance()));
		return sb.toString();
	}

}
