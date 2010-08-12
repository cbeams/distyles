package com.bank.service.internal;

import com.bank.service.FeePolicy;


public class FlatFeePolicy implements FeePolicy {

	private final double flatFee;

	public FlatFeePolicy(double flatFee) {
		this.flatFee = flatFee;
	}
	
	public double calculateFee(double transferAmount) {
		return flatFee;
	}

}

