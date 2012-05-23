package com.bank.repository.internal;

import java.util.HashMap;
import java.util.Map;

import com.bank.domain.Account;
import com.bank.repository.AccountNotFoundException;
import com.bank.repository.AccountRepository;

public class SimpleAccountRepository implements AccountRepository {

	public static class Data {
		public static final String A123_ID = "A123";
		public static final String C456_ID = "C456";
		public static final String Z999_ID = "Z999"; // NON EXISTENT ID
		public static final double A123_INITIAL_BAL = 100.00;
		public static final double C456_INITIAL_BAL = 0.00;
	}


	@SuppressWarnings("serial")
	private final Map<String, Account> accountsById = new HashMap<String, Account>() {{
		put(Data.A123_ID, new Account(Data.A123_ID, Data.A123_INITIAL_BAL));
		put(Data.C456_ID, new Account(Data.C456_ID, Data.C456_INITIAL_BAL));
	}};

	@Override
	public Account findById(String acctId) {
		return Account.copy(nullSafeAccountLookup(acctId));
	}

	@Override
	public void updateBalance(Account account) {
		Account actualAccount = nullSafeAccountLookup(account.getId());
		actualAccount.setBalance(account.getBalance());
	}

	private Account nullSafeAccountLookup(String acctId) {
		Account account = accountsById.get(acctId);
		if (account == null)
			throw new AccountNotFoundException(acctId);
		return account;
	}

}
