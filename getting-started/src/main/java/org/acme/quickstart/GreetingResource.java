package org.acme.quickstart;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Locale;

@Path("/hello")
public class GreetingResource {

    @Inject
    Config config;

    @GET
    @Path("/config")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloConfig(){
        config.getPropertyNames().forEach(p -> {
            System.out.println(p);
        });
        return config.getValue("gretting.message", String.class);
    }

    @ConfigProperty(name = "greeting.message")
    String message;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String hello(){
        return message;
    }

    @ConfigProperty(name = "greeting.upper-case", defaultValue = "true")
    boolean upperCase;
    @GET
    @Path("/optional")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloOptional(){
        return upperCase ? message.toUpperCase() : message;
    }

    @ConfigProperty(name = "greeting.suffix")
    List<String> suffixes;
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloList(){
        return message + suffixes.get(1);
    }

    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(
        @Context UriInfo uriInfo,
        @QueryParam("order") Order order,
        @NotBlank @HeaderParam("authorization") String authorization
    ) {

        return String.format("URI: %s - Order %s - Authorization: %s",
        uriInfo.getAbsolutePath(), order, authorization);
    }*/

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public void create(String message){
        System.out.println("Create");
    }

    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String update(String message){
        System.out.println("Update");
        return message;
    }

    @DELETE
    public void delete(String message){
        System.out.println("Delete");
    }

    @LOCK
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String lockResource(@PathParam("id") long id){
        return id + " locked";
    }

    public static enum Order{
        asc, desc;
    }
}