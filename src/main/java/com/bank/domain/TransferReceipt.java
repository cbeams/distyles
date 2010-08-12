package com.bank.domain;

import static java.lang.String.format;

public class TransferReceipt {

	private double transferAmount;
	private double feeAmount;
	private Account initialSourceAccountCopy;
	private Account initialDestinationAccountCopy;
	private Account finalSourceAccountCopy;
	private Account finalDestinationAccountCopy;
	
	
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}
	
	public double getTransferAmount() {
		return transferAmount;
	}

	
	public void setFeeAmount(double feeAmount) {
		this.feeAmount = feeAmount;
	}
	
	public double getFeeAmount() {
		return feeAmount;
	}


	public void setInitialSourceAccount(Account account) {
		initialSourceAccountCopy = Account.copy(account);
	}
	
	public void setFinalSourceAccount(Account account) {
		finalSourceAccountCopy = Account.copy(account);
	}
	
	public Account getFinalSourceAccount() {
		return finalSourceAccountCopy;
	}

	
	public void setInitialDestinationAccount(Account account) {
		initialDestinationAccountCopy = Account.copy(account);
	}
	
	public void setFinalDestinationAccount(Account account) {
		finalDestinationAccountCopy = Account.copy(account);
	}
	
	public Account getFinalDestinationAccount() {
		return finalDestinationAccountCopy;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
			.append(format("Transferred %.2f from account %s to %s, with fee amount: %.2f\n",
					transferAmount, initialSourceAccountCopy.getId(),
					initialDestinationAccountCopy.getId(), feeAmount))
			.append(format("\tinitial balance for account %s: %.2f; new balance: %.2f\n",
					initialSourceAccountCopy.getId(),
					initialSourceAccountCopy.getBalance(),
					finalSourceAccountCopy.getBalance()))
			.append(format("\tinitial balance for account %s: %.2f; new balance: %.2f\n",
					initialDestinationAccountCopy.getId(),
					initialDestinationAccountCopy.getBalance(),
					finalDestinationAccountCopy.getBalance()));
		return sb.toString();
	}

}
