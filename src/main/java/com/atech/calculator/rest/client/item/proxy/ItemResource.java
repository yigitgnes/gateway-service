package com.atech.calculator.rest.client.item.proxy;

import com.atech.calculator.rest.client.item.model.Item;
import com.atech.calculator.rest.client.item.model.MonthlySalesDataDTO;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@Path("/item")
@Authenticated
public class ItemResource {

    private Logger LOGGER = Logger.getLogger(ItemResource.class);
    @RestClient
    ItemProxy itemProxy;

    @GET
    @PermitAll
    public Response getAllItems(@DefaultValue("0") @QueryParam("page") int page,
                                @DefaultValue("10") @QueryParam("size") int size) {
        try {
            page = (page < 1) ? 1 : page;
            size = (size <= 0) ? 10 : size;

            return Response.ok(itemProxy.getAllItems(page, size).readEntity(PagedResult.class)).build();
        } catch (WebApplicationException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error retrieving items").build();
        }
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
            description = "Fetches a specific expense from the database using the provided ID.",
            summary = "Get Expense by ID"
    )
    public Response getItemById(@PathParam("id") Long id) {
        LOGGER.info("Fetching expense by ID: " + id);
        if (id <= 0 || id == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid ID provided").build();
        }
        try {
            Optional<Item> itemOptional = itemProxy.getItemById(id);
            return itemOptional.isPresent() ? Response.ok(itemOptional.get()).build() : Response.status(Response.Status.NOT_FOUND).build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when fetching item by ID", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Item not found").build();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch item by ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    @POST
    @PermitAll
    @Operation(
            summary = "Post New Item",
            description = "Saves a new Item into the database."
    )
    public Response createExpense(Item item) {
        itemProxy.createItem(item);
        return Response.ok(item).build();
    }

    @PUT
    @PermitAll
    @Operation(
            summary = "Update An Item",
            description = "Updates an existing Item, if the item is exist"
    )
    public Response updateExpense(Item item) {
        itemProxy.updateItem(item);
        return Response.ok(item).build();
    }

    @DELETE
    @RolesAllowed({"admin"})
    @Path("/{id}")
    @Operation(
            description = "Deletes the Item by the given ID",
            summary = "Delete the Item"
    )
    public Response deleteExpense(@PathParam("id") Long id) {
        itemProxy.deleteItem(id);
        return Response.noContent().build();
    }

    @GET
    @PermitAll
    @Path("/sales/monthly")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MonthlySalesDataDTO> getMonthlySales() {
        return itemProxy.getMonthlySales();
    }

    @GET
    @PermitAll
    @Path("/earning/monthly")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MonthlySalesDataDTO> getMonthlyEarnings() {
        return itemProxy.getMonthlyEarnings();
    }

    public static class PagedResult<T> {
        public List<T> items;
        public int page;
        public int size;
        public long totalCount;

        public PagedResult() {
        }

        public PagedResult(List<T> items, int page, int size, long totalCount) {
            this.items = items;
            this.page = page;
            this.size = size;
            this.totalCount = totalCount;
        }
    }

}
