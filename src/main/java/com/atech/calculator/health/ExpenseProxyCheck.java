package com.atech.calculator.health;

import com.atech.calculator.rest.client.expense.proxy.ExpenseProxy;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Readiness
public class ExpenseProxyCheck implements HealthCheck {

    @RestClient
    ExpenseProxy expenseProxy;

    private Instant currentInstant = Instant.now();

    public void updateCurrentInstant() {
        this.currentInstant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }
    public static final String EXPENSE_HEALTH_CHECK = "[Expense-Service-Health-Check]";
    @Override
    public HealthCheckResponse call() {
        updateCurrentInstant();
        expenseProxy.getExpenseById(1L);
        return HealthCheckResponse.named(EXPENSE_HEALTH_CHECK)
                .withData("[get-expense.instant]", currentInstant.toString())
                .up()
                .build();
    }
}
