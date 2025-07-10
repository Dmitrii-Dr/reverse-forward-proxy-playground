package com.dmdr.gateway.resource;
import com.dmdr.gateway.model.RegistryUrlDto;
import com.dmdr.gateway.service.RegistrationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Path("proxy-gateway/registry")
@Slf4j
public class RegistrationResource {
        
    @Inject
    RegistrationService registrationService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String registry(RegistryUrlDto request) {
        registrationService.register(request);
        log.info("Registered {} : {}", request.getApplication(), request.getUrl());
        return "Registered: " + request.getApplication() + ":" + request.getUrl();
    }
}
