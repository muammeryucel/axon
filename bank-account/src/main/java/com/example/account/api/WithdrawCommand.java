package com.example.account.api;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateVersion;

public class WithdrawCommand {

	@TargetAggregateIdentifier
	private String accountId;
	
	@TargetAggregateVersion
	private Long version;
	
	private BigDecimal amount;

	public WithdrawCommand(String accountId, Long version, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.version = version;
		this.amount = amount;
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
}
