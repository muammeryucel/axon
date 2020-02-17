package com.example.account.query;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.account.api.AccountCreatedEvent;

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

}
