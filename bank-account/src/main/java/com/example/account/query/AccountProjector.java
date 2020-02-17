package com.example.account.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.account.api.AccountCreatedEvent;
import com.example.account.api.FindByAccountIdQuery;

@Component
public class AccountProjector {

	@Autowired
	private AccountDetailsRepository repository;

	@EventHandler
	public void handleAccountCreatedEvent(AccountCreatedEvent event) {
		AccountDetails entity = new AccountDetails();
		entity.setAccountId(event.getAccountId());
		repository.save(entity);
	}

	@QueryHandler
	public AccountDetails findByAccountId(FindByAccountIdQuery query) {
		return repository.findByAccountId(query.getAccountId());
	}
}
