package com.bank.repository;

import com.bank.domain.Account;

public interface AccountRepository {

	Account findById(String srcAcctId);

	void updateBalance(Account dstAcct);

}
