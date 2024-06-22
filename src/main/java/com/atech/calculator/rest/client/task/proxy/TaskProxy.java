package com.atech.calculator.rest.client.task.proxy;

import com.atech.calculator.rest.client.task.model.Task;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/tasks")
@RegisterRestClient(configKey = "calculator-proxy")
public interface TaskProxy {

    @GET
    List<Task> getAllTasks(@DefaultValue("") @QueryParam("category") String category);

    @GET
    @Path("/{id}")
    Response getTaskById(@PathParam("id") Long id);

    @POST
    Response createTask(Task task);

    @PUT
    @Path("/{id}")
    Response updateTask(@PathParam("id") Long id, @RequestBody Task receivedTask);

    @DELETE
    @Path("/{id}")
    Response deleteTask(@PathParam("id") Long id);
}
