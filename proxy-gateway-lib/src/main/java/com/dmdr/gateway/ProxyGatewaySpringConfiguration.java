package com.dmdr.gateway;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Lazy;
import lombok.extern.slf4j.Slf4j;


@Component
@Lazy(false)
@Slf4j
public class ProxyGatewaySpringConfiguration extends ProxyGatewayConfiguration {
    public ProxyGatewaySpringConfiguration() {
        super();
        log.info("ProxyGatewaySpringConfiguration END");
    }
}
