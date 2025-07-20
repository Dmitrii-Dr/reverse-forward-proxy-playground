package com.dmdr.gateway;

import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ProxyGatewayJakartaConfiguration extends ProxyGatewayConfiguration {
    public ProxyGatewayJakartaConfiguration() {
        super();
        log.info("ProxyGatewayJakartaConfiguration END");
    }
}
