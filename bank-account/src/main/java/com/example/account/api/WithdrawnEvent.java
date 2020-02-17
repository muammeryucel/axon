package com.example.account.api;

import java.math.BigDecimal;

public class WithdrawnEvent {

	private String accountId;
	
	private BigDecimal amount;

	public WithdrawnEvent(String accountId, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
}
