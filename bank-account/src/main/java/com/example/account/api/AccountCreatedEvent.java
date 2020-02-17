package com.example.account.api;

public class AccountCreatedEvent {

	private String accountId;

	public AccountCreatedEvent(String accountId) {
		super();
		this.accountId = accountId;
	}
	
	public String getAccountId() {
		return accountId;
	}
}
