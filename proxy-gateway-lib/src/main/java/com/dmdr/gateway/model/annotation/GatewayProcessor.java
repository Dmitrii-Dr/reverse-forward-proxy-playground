package com.dmdr.gateway.model.annotation;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Path;

import com.dmdr.gateway.model.RegistryUrlDto;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class GatewayProcessor {
    private static final String REGISTRATION_URL = "http://proxy-gateway:8080/proxy-gateway/registry"; // Adjust as needed

    @PostConstruct
    public void processGateways() {
        log.info("GatewayProcessor BEGIN");
        Reflections reflections = new Reflections("com.dmdr");
        Set<Class<?>> gatewayClasses = reflections.getTypesAnnotatedWith(Gateway.class);
        Client client = ClientBuilder.newClient();

        for (Class<?> clazz : gatewayClasses) {
            Gateway gateway = clazz.getAnnotation(Gateway.class);
            String classPath = "";
            Path classPathAnn = clazz.getAnnotation(Path.class);
            if (classPathAnn != null) {
                classPath = classPathAnn.value();
            }
            for (Method method : clazz.getDeclaredMethods()) {
                Path methodPathAnn = method.getAnnotation(Path.class);
                if (methodPathAnn != null) {
                    String methodPath = methodPathAnn.value();
                    String fullPath = combinePaths(classPath, methodPath);

                    // Create DTO and send registration request
                    RegistryUrlDto dto = new RegistryUrlDto();
                    dto.setApplication(gateway.url());
                    dto.setHost(gateway.url());
                    dto.setUrl(fullPath);

                    log.info("Registrate {}", dto);
                    //TODO Validate response. Retry if 5xx, Ecxeption if 4xx
                    var response = client.target(REGISTRATION_URL)
                          .request(MediaType.WILDCARD)
                          .post(Entity.entity(dto, MediaType.APPLICATION_JSON));
                    log.info("Registration response: HTTP {} - {}", response.getStatus(), response.getStatusInfo().getReasonPhrase());
                    int status = response.getStatus();
                    if (status >= 400 && status < 600) {
                        response.close();
                        throw new RuntimeException("Failed to register gateway: HTTP " + status + " - " + response.getStatusInfo().getReasonPhrase());
                    }
                    response.close();
                }
            }
        }
        client.close();
    }

    public void print(){
        log.info("Hello Gateway!");
    }

    private String combinePaths(String classPath, String methodPath) {
        if (classPath.endsWith("/")) classPath = classPath.substring(0, classPath.length() - 1);
        if (!methodPath.startsWith("/")) methodPath = "/" + methodPath;
        return classPath + methodPath;
    }
}
