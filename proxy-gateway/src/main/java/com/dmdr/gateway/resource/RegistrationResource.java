package com.dmdr.gateway.resource;

import com.dmdr.gateway.model.RegistryUrlDto;
import com.dmdr.gateway.service.RegistrationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/")
public class RegistrationResource {

    @Inject
    RegistrationService registrationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("registry")
    public String registry(RegistryUrlDto request) {
        registrationService.register(request);
        log.info("Registered {} : {}", request.getApplication(), request.getUrl());
        return "Registered: " + request.getApplication() + ":" + request.getUrl();
    }

    @GET
    @Path("{service}/{endpoint}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response proxyGet(
            @PathParam("service") String service,
            @PathParam("endpoint") String endpoint) {
        String targetUrl = registrationService.getUrl(service, endpoint);
        if (targetUrl == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Service or endpoint not found").build();
        }
        Client client = ClientBuilder.newClient();
        Response response = client.target(targetUrl).request().get();
        String result = response.readEntity(String.class);
        response.close();
        client.close();
        return Response.ok(result).build();
    }

}
