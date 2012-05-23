package com.bank.domain;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = -4041323379751753579L;

	private final String targetAccountId;
	private final double targetAccountBalance;
	private final double attemptedAmount;

	public InsufficientFundsException(
			String targetAccountId, double targetAccountBalance, double attemptedAmount) {
		this.targetAccountId = targetAccountId;
		this.targetAccountBalance = targetAccountBalance;
		this.attemptedAmount = attemptedAmount;
	}

	public String getTargetAccountId() {
		return this.targetAccountId;
	}

	public double getTargetAccountBalance() {
		return this.targetAccountBalance;
	}

	public double getAttemptedAmount() {
		return this.attemptedAmount;
	}

	public double getOverage() {
		return getAttemptedAmount() - getTargetAccountBalance();
	}

	public String toString() {
		return String.format(
			"Failed to transfer $%.2f from account %s. Reason: insufficient funds\n" +
			"\tcurrent balance for target account is $%.2f\n" +
			"\ttransfer overage is $%.2f\n",
			this.getAttemptedAmount(), this.getTargetAccountId(),
			this.getTargetAccountBalance(), this.getOverage());
	}
}
