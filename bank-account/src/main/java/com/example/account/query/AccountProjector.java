package com.example.account.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.account.api.AccountCreatedEvent;
import com.example.account.api.DepositedEvent;
import com.example.account.api.FindByAccountIdQuery;
import com.example.account.api.WithdrawnEvent;

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

	@EventHandler
	public void handleDepositedEvent(DepositedEvent event) {
		AccountDetails entity = repository.findByAccountId(event.getAccountId());
		entity.setBalance(entity.getBalance().add(event.getAmount()));
		repository.save(entity);
	}

	@EventHandler
	public void handleWithdrawnEvent(WithdrawnEvent event) {
		AccountDetails entity = repository.findByAccountId(event.getAccountId());
		entity.setBalance(entity.getBalance().subtract(event.getAmount()));
		repository.save(entity);
	}

	@QueryHandler
	public AccountDetails findByAccountId(FindByAccountIdQuery query) {
		return repository.findByAccountId(query.getAccountId());
	}
}
