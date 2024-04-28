package com.atech.calculator.health;

import com.atech.calculator.rest.client.item.proxy.ItemProxy;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Readiness
public class ItemProxyCheck implements HealthCheck {
    @RestClient
    ItemProxy itemProxy;
    private Instant currentInstant = Instant.now();
    public void updateCurrentInstant() {
        this.currentInstant = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }
    public static final String ITEAM_HEALTH_CHECK = "[Item-Service-Health-Check]";
    @Override
    public HealthCheckResponse call() {
        updateCurrentInstant();
        itemProxy.getItemById(1L);
        return HealthCheckResponse.named(ITEAM_HEALTH_CHECK)
                .withData("[get-item.instant]", currentInstant.toString())
                .up()
                .build();
    }
}
