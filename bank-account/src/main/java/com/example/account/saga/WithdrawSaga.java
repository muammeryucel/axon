package com.example.account.saga;

import java.math.BigDecimal;
import java.time.Duration;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.account.api.DepositCommand;
import com.example.account.api.WithdrawConfirmedEvent;
import com.example.account.api.WithdrawnEvent;

@Saga
public class WithdrawSaga {

	@Autowired
	private transient DeadlineManager deadlineManager;

	@Autowired
	private transient CommandGateway commandGateway;

	private String accountId;
	private BigDecimal amount;
	private String scheduleId;

	@StartSaga
	@SagaEventHandler(associationProperty = "accountId")
	public void on(WithdrawnEvent event) {
		accountId = event.getAccountId();
		amount = event.getAmount();
		scheduleId = deadlineManager.schedule(Duration.ofSeconds(15), "withdraw-deadline");
	}

	@DeadlineHandler(deadlineName = "withdraw-deadline")
	public void handleDeadline() {
		commandGateway.sendAndWait(new DepositCommand(accountId, amount));
		SagaLifecycle.end();
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "accountId")
	public void on(WithdrawConfirmedEvent event) {
		deadlineManager.cancelSchedule("withdraw-deadline", scheduleId);
	}
}
