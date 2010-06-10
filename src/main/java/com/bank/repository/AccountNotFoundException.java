package com.bank.repository;

@SuppressWarnings("serial")
public class AccountNotFoundException extends RuntimeException {

	public AccountNotFoundException(String acctId) {
		super(String.format("account with id [%s] could not be found", acctId));
	}
	
}
