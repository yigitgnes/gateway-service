package com.atech.calculator.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class GatewayCheck implements HealthCheck {
    public static final String GATEWAY_HEALTH_CHECK = "[Gateway-Health-Check]";

    @Override
    public HealthCheckResponse call(){
        return HealthCheckResponse.named(GATEWAY_HEALTH_CHECK)
                .up()
                .build();
    }
}
