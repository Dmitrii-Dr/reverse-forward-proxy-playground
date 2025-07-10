package com.dmdr.proxy.lab.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import com.dmdr.gateway.model.annotation.GatewayProcessor;

import io.quarkus.runtime.Startup;

@ApplicationScoped
@Startup
@Slf4j
public class StartupConfuguration {
    private final GatewayProcessor gatewayProcessor;

    @Inject
    public StartupConfuguration(GatewayProcessor gatewayProcessor) {
        log.info("StartupConfuguration BEGIN");
        this.gatewayProcessor = gatewayProcessor;
    }
}
