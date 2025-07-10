package com.dmdr.proxy.lab.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import com.dmdr.gateway.model.annotation.Gateway;

@Path("/customers")
@Gateway(url = "customer-service")
@ApplicationScoped
public class CustomerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/test")
    public List<String> getCustomers() {
        return List.of("Alice3", "Bob", "Charlie");
    }

    
}