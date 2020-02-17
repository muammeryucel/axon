package com.example.account.api;

public class WithdrawConfirmedEvent {

	private String accountId;

	public WithdrawConfirmedEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
