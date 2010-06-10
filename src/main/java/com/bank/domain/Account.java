package com.bank.domain;


public class Account {

	private final String id;
	private double balance;

	public Account(String id, double initialBalance) {
		this.id = id;
		this.balance = initialBalance;
	}

	public void debit(double amount) throws InsufficientFundsException {
		assertValid(amount);

		if (amount > balance)
			throw new InsufficientFundsException(this, amount);

		balance -= amount;
	}

	public void credit(double amount) {
		assertValid(amount);
		
		balance += amount;
	}
	
	public String getId() {
		return id;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	private void assertValid(double amount) {
		if (!(amount > 0.00))
			throw new IllegalArgumentException("amount must be greater than zero");
	}

	public static Account copy(Account account) {
		return new Account(account.getId(), account.getBalance());
	}

}
