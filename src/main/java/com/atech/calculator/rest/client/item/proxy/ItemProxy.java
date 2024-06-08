package com.atech.calculator.rest.client.item.proxy;

import com.atech.calculator.rest.client.item.model.Item;
import com.atech.calculator.rest.client.item.model.MonthlySalesDataDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;
import java.util.Optional;

@Path("/item")
@RegisterRestClient(configKey = "calculator-proxy")
public interface ItemProxy {

    @GET
    List<Item> getAllItems();

    @GET
    @Path("/{id}")
    Optional<Item> getItemById(@PathParam("id") Long id);

    @POST
    Response createItem(Item item);

    @PUT
    Response updateItem(Item item);

    @DELETE
    @Path("/{id}")
    Response deleteItem(@PathParam("id") Long id);

    @GET
    @Path("/sales/monthly")
    List<MonthlySalesDataDTO> getMonthlySales();

    @GET
    @Path("/earning/monthly")
    List<MonthlySalesDataDTO> getMonthlyEarnings();

}
