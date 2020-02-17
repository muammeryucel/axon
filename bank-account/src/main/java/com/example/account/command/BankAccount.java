package com.example.account.command;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.example.account.api.AccountCreatedEvent;
import com.example.account.api.CreateAccountCommand;
import com.example.account.api.DepositCommand;
import com.example.account.api.DepositedEvent;
import com.example.account.api.WithdrawCommand;
import com.example.account.api.WithdrawnEvent;

@Aggregate
public class BankAccount {

	@AggregateIdentifier
	private String accountId;

	private BigDecimal balance;

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
		balance = BigDecimal.ZERO;
	}

	@CommandHandler
	public void handle(DepositCommand command) {
		AggregateLifecycle.apply(new DepositedEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(DepositedEvent event) {
		balance = balance.add(event.getAmount());
	}

	@CommandHandler
	public void handle(WithdrawCommand command) {
		if (balance.compareTo(command.getAmount()) < 0) {
			throw new IllegalStateException("Insufficient balance!");
		}
		AggregateLifecycle.apply(new WithdrawnEvent(accountId, command.getAmount()));
	}

	@EventSourcingHandler
	public void on(WithdrawnEvent event) {
		balance = balance.subtract(event.getAmount());
	}
}
