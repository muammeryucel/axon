package com.example.account.api;

import java.util.UUID;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateAccountCommand {
	
	@TargetAggregateIdentifier
	private String accountId = UUID.randomUUID().toString();

	public String getAccountId() {
		return accountId;
	}
}
