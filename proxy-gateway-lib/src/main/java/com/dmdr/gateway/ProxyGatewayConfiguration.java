package com.dmdr.gateway;

import com.dmdr.gateway.model.annotation.GatewayProcessor;

public abstract class ProxyGatewayConfiguration {
    public ProxyGatewayConfiguration() {
        new GatewayProcessor().processGateways();
    }

}
