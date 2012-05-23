package com.bank.service.internal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link VariableFeePolicy}.
 */
public class VariableFeePolicyTests {

	@Test
	public void testVariableFee() {
		VariableFeePolicy feePolicy = new VariableFeePolicy();
		feePolicy.setFeePercentage(1.00); // 1% fee
		feePolicy.setMinimumFee(1.00); // $1.00

		assertThat(feePolicy.calculateFee(1000.00), equalTo(10.00));
		assertThat(feePolicy.calculateFee(100.00), equalTo(1.00));
		assertThat(feePolicy.calculateFee(10.00), equalTo(1.00));
	}

}
