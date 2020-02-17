package com.example.account.api;

public class FindByAccountIdQuery {

	private String accountId;

	public FindByAccountIdQuery(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
