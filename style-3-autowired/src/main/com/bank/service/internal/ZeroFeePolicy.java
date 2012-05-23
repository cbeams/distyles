package com.bank.service.internal;

import com.bank.service.FeePolicy;

public class ZeroFeePolicy implements FeePolicy {

	private static final double ZERO_AMOUNT = 0.00d;

	@Override
	public double calculateFee(double transferAmount) {
		return ZERO_AMOUNT;
	}

}
