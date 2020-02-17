package com.example.account.command;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.account.api.AccountCreatedEvent;
import com.example.account.api.CreateAccountCommand;

@Aggregate
public class BankAccount {

	@AggregateIdentifier
	private String accountId;

	public BankAccount() {
		super();
	}

	@CommandHandler
	public BankAccount(CreateAccountCommand command) {
		this();
		AccountCreatedEvent event = new AccountCreatedEvent(command.getAccountId());
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(AccountCreatedEvent event) {
		accountId = event.getAccountId();
	}
}
