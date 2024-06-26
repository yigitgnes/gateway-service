package com.atech.calculator.rest.client.expense.proxy;

import com.atech.calculator.rest.client.expense.model.Expense;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/expense")
@RegisterRestClient(configKey = "calculator-proxy")
@RegisterClientHeaders
public interface ExpenseProxy {

    @GET
    Response getAllExpenses(@DefaultValue("0") @QueryParam("page") int page,
                            @DefaultValue("10") @QueryParam("size") int size);

    @GET
    @Path("/{id}")
    Response getExpenseById(@PathParam("id") Long id);

    @POST
    Response createExpense(Expense expense);

    @PUT
    Response updateExpense(Expense expense);

    @DELETE
    @Path("/{id}")
    Response deleteExpense(@PathParam("id") Long id);

    @GET
    @Path("/monthly")
    Response getMonthlyExpenseForCurrentYear();

}
