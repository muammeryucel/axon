package com.example.account.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.account.api.CreateAccountCommand;

@RestController
public class AccountController {

	@Autowired
	private CommandGateway commandGateway;

	@PostMapping("/accounts")
	public ResponseEntity<?> createAccount() {
		String accountId = commandGateway.sendAndWait(new CreateAccountCommand());
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountId}").buildAndExpand(accountId).toUri())
				.build();
	}

}
