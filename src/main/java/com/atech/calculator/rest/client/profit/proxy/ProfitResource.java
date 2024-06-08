package com.atech.calculator.rest.client.profit.proxy;

import com.atech.calculator.rest.client.profit.model.Profit;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/profit")
@Authenticated
public class ProfitResource {

    @RestClient
    ProfitProxy profitProxy;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Returns the all over profit",
            summary = "Get Profit"
    )
    public Response getProfit() {
        Profit profit = profitProxy.getProfit();
        return Response.ok(profit).build();
    }
}
