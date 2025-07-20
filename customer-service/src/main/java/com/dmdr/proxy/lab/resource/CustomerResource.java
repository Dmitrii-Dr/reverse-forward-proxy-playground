package com.dmdr.proxy.lab.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import com.dmdr.gateway.model.annotation.Gateway;

@Gateway("customer-service")
@ApplicationScoped
public class CustomerResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/customers")
    public List<String> getCustomers() {
        return List.of("Alice3", "Bob", "Charlie");
    }   
}