package com.bank.repository;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6172127909827414291L;

	public AccountNotFoundException(String acctId) {
		super(String.format("account with id [%s] could not be found", acctId));
	}

}
