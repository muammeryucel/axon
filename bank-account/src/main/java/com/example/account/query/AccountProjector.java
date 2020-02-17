package com.example.account.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.SequenceNumber;
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
	public void handleAccountCreatedEvent(AccountCreatedEvent event, @SequenceNumber Long version) {
		AccountDetails entity = new AccountDetails();
		entity.setAccountId(event.getAccountId());
		entity.setVersion(version);
		repository.save(entity);
	}

	@EventHandler
	public void handleDepositedEvent(DepositedEvent event, @SequenceNumber Long version) {
		AccountDetails entity = repository.findByAccountId(event.getAccountId());
		entity.setBalance(entity.getBalance().add(event.getAmount()));
		entity.setVersion(version);
		repository.save(entity);
	}

	@EventHandler
	public void handleWithdrawnEvent(WithdrawnEvent event, @SequenceNumber Long version) {
		AccountDetails entity = repository.findByAccountId(event.getAccountId());
		entity.setBalance(entity.getBalance().subtract(event.getAmount()));
		entity.setVersion(version);
		repository.save(entity);
	}

	@QueryHandler
	public AccountDetails findByAccountId(FindByAccountIdQuery query) {
		return repository.findByAccountId(query.getAccountId());
	}
}
