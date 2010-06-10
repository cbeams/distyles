package com.bank.service.internal;

import com.bank.service.FeePolicy;


public class VariableFeePolicy implements FeePolicy {
	
	private double feePercentage;
	private double minimumFee;

	public void setMinimumFee(double minimumFee) {
		this.minimumFee = minimumFee;
	}
	
	public void setFeePercentage(double feePercentage) {
		this.feePercentage = feePercentage;
	}

	@Override
	public double calculateFee(double transferAmount) {
		double variableFee = transferAmount * (feePercentage/100);
		return variableFee > minimumFee ? variableFee : minimumFee;
	}

}
