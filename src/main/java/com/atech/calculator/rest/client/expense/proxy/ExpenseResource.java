package com.atech.calculator.rest.client.expense.proxy;

import com.atech.calculator.rest.client.expense.model.Expense;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/expense")
@Authenticated
public class ExpenseResource {

    private Logger LOGGER = Logger.getLogger(ExpenseResource.class);
    @RestClient
    ExpenseProxy expenseProxy;

    /**
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    @Operation(
            description = "Returns all the Expenses saved into the database.",
            summary = "Get All Expenses"
    )
    @Timeout(3000)
    @Retry(maxRetries = 2)
    public Response getAllExpenses() {
        LOGGER.info("Fetching all expenses");
        try {
            List<Expense> expenses = expenseProxy.getAllExpenses();
            return Response.ok(expenses).build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when fetching all expenses", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve expenses", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving expenses").build();
        }
    }

    /**
     *
     * @return
     */
    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Fetches a specific expense from the database using the provided ID.",
            summary = "Get Expense by ID"
    )
    @Timeout(3000)
    @Retry(maxRetries = 2)
    public Response getExpenseById(@PathParam("id") Long id) {
        LOGGER.info("Fetching expense by ID: " + id);
        try {
            Response response = expenseProxy.getExpenseById(id);
            return response;
        } catch (NotFoundException e) {
            LOGGER.error("Expense not found", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Expense not found").build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when fetching expense by ID", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Expense not found").build();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch expense by ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     *
     * @return
     */
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Post New Expense",
            description = "Saves a new Expense into the database."
    )
    @Timeout(3000)
    @Retry(maxRetries = 2)
    public Response createExpense(Expense expense) {
        LOGGER.info("Creating an expense");
        try {
            Response response = expenseProxy.createExpense(expense);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when creating an expense", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to create expense", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     *
     * @return
     */
    @PUT
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            summary = "Update An Expense",
            description = "Updates an existing Expense."
    )
    @Timeout(3000)
    @Retry(maxRetries = 2)
    public Response updateExpense(Expense expense) {
        LOGGER.info("Updating an expense");
        try {
            Response response = expenseProxy.updateExpense(expense);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when updating an expense", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to update expense", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     *
     * @return
     */
    @DELETE
    @RolesAllowed({"admin"})
    @Path("/{id}")
    @Operation(
            description = "Deletes the Expense by the given ID",
            summary = "Delete the Expense"
    )
    @Timeout(3000)
    @Produces(MediaType.APPLICATION_JSON)
    @Retry(maxRetries = 2)
    public Response deleteExpense(@PathParam("id") Long id) {
        LOGGER.warn("Deleting an expense ID: " + id);
        try {
            Response response = expenseProxy.deleteExpense(id);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        } catch (NotFoundException e) {
            LOGGER.error("Expense not found for deletion", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Expense not found").build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when deleting an expense", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to delete expense", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

}
