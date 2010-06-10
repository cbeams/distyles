package com.bank.domain;

import static java.lang.String.format;


@SuppressWarnings("serial")
public class InsufficientFundsException extends Exception {
	private final Account targetAccount;
	private final double attemptedAmount;
	
	public InsufficientFundsException(Account targetAccount, double attemptedAmount) {
		this.targetAccount = Account.copy(targetAccount);
		this.attemptedAmount = attemptedAmount;
	}

	public String getTargetAccountId() {
		return targetAccount.getId();
	}

	public double getTargetAccountBalance() {
		return targetAccount.getBalance();
	}

	public double getAttemptedAmount() {
		return attemptedAmount;
	}
	
	public double getOverage() {
		return getAttemptedAmount() - getTargetAccountBalance();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder()
			.append(format("Failed to transfer $%.2f from account %s. " +
					"Reason: insufficient funds\n", getAttemptedAmount(), getTargetAccountId()))
			.append(format("\tcurrent balance for target account is $%.2f\n", getTargetAccountBalance()))
			.append(format("\ttransfer overage is $%.2f\n", getOverage()));
		return sb.toString();
	}
}
