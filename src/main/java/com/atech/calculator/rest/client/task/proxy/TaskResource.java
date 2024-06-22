package com.atech.calculator.rest.client.task.proxy;

import com.atech.calculator.rest.client.task.model.Task;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

@Path("/tasks")
@Authenticated
public class TaskResource {

    private Logger LOGGER = Logger.getLogger(TaskResource.class);
    @RestClient
    TaskProxy taskProxy;

    /**
     *
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public List<Task> getAllTasks(@QueryParam("category") @DefaultValue("") String category){
        return taskProxy.getAllTasks(category);
    }

    /**
     *
     */
    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTaskById(@PathParam("id") Long id) {
        LOGGER.info("Fetching task by ID: " + id);
        try {
            Response response = taskProxy.getTaskById(id);
            return response;
        } catch (NotFoundException e) {
            LOGGER.error("Task not found", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when fetching task by ID", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
        } catch (Exception e) {
            LOGGER.error("Failed to fetch task by ID", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     *
     */
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTask(Task task) {
        LOGGER.info("Creating a task");
        try {
            Response response = taskProxy.createTask(task);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when creating a task", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to create task", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     *
     */
    @PUT
    @Path("/{id}")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTask(@PathParam("id") Long id, @RequestBody Task receivedTask) {
        LOGGER.info("Updating a task");
        try {
            Response response = taskProxy.updateTask(id, receivedTask);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when updating the task", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to update task", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }

    /**
     *
     */
    @DELETE
    @PermitAll
    @Path("/{id}")
    public Response deleteExpense(@PathParam("id") Long id) {
        LOGGER.warn("Deleting the task ID: " + id);
        try {
            Response response = taskProxy.deleteTask(id);
            return Response.status(response.getStatus()).entity(response.getEntity()).build();
        } catch (NotFoundException e) {
            LOGGER.error("Task not found for deletion", e);
            return Response.status(Response.Status.NOT_FOUND).entity("Task not found").build();
        } catch (WebApplicationException e) {
            LOGGER.error("Web application error when deleting the Task", e);
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        } catch (Exception e) {
            LOGGER.error("Failed to delete task", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
        }
    }
}
