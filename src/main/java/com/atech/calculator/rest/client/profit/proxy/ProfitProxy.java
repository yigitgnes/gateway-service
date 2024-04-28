package com.atech.calculator.rest.client.profit.proxy;

import com.atech.calculator.rest.client.profit.model.Profit;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/profit")
@RegisterRestClient(configKey = "calculator-proxy")
public interface ProfitProxy {

    @GET
    Profit getProfit();
}
