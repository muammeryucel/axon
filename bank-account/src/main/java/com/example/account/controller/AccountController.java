package com.example.account.controller;

import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.account.api.CreateAccountCommand;
import com.example.account.api.FindByAccountIdQuery;
import com.example.account.query.AccountDetails;

@RestController
public class AccountController {

	@Autowired
	private CommandGateway commandGateway;

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

}
