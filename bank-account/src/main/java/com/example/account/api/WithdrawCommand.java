package com.example.account.api;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class WithdrawCommand {

	@TargetAggregateIdentifier
	private String accountId;
	
	private BigDecimal amount;

	public WithdrawCommand(String accountId, BigDecimal amount) {
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
