package com.example.account.controller;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.account.api.CreateAccountCommand;
import com.example.account.api.DepositCommand;
import com.example.account.api.FindByAccountIdQuery;
import com.example.account.api.WithdrawCommand;
import com.example.account.api.WithdrawConfirmedEvent;
import com.example.account.query.AccountDetails;

@RestController
public class AccountController {

	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private EventGateway eventGateway;

	@Autowired
	private QueryGateway queryGateway;

	@PostMapping("/accounts")
	public ResponseEntity<?> createAccount() {
		String accountId = commandGateway.sendAndWait(new CreateAccountCommand());
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountId}").buildAndExpand(accountId).toUri())
				.build();
	}

	@GetMapping("/accounts/{accountId}")
	public CompletableFuture<AccountDetails> findAccountByAccountId(@PathVariable("accountId") String accountId) {
		return queryGateway.query(new FindByAccountIdQuery(accountId), AccountDetails.class);
	}

	@PostMapping("/deposit/{accountId}")
	public ResponseEntity<?> deposit(@PathVariable("accountId") String accountId,
			@RequestParam("amount") BigDecimal amount) {
		commandGateway.sendAndWait(new DepositCommand(accountId, amount));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/withdraw/{accountId}")
	public ResponseEntity<?> withdraw(@PathVariable("accountId") String accountId,
			@RequestParam("version") Long version, @RequestParam("amount") BigDecimal amount) {
		commandGateway.sendAndWait(new WithdrawCommand(accountId, version, amount));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/confirm/{accountId}")
	public ResponseEntity<?> confirmWithdraw(@PathVariable("accountId") String accountId) {
		eventGateway.publish(new WithdrawConfirmedEvent(accountId));
		return ResponseEntity.ok().build();
	}

}
