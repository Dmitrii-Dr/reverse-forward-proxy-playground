package com.dmdr.proxy.lab.configuration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import com.dmdr.gateway.ProxyGatewayJakartaConfiguration;

import io.quarkus.runtime.Startup;

@ApplicationScoped
@Startup
@Slf4j
public class StartupConfuguration {
     private final ProxyGatewayJakartaConfiguration proxyGatewayJakartaConfiguration;

    @Inject
    public StartupConfuguration(ProxyGatewayJakartaConfiguration gatewayProcessor) {
        log.info("StartupConfuguration BEGIN");
        this.proxyGatewayJakartaConfiguration = gatewayProcessor;
    }
}
