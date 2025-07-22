package com.dmdr.gateway.model.annotation;

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


@Slf4j
public class GatewayProcessor {
    private static final String REGISTRATION_URL = "http://proxy-gateway:8080/registry"; // Adjust as needed

    public void processGateways() {
        log.info("GatewayProcessor BEGIN");
        Reflections reflections = new Reflections("com");
        Set<Class<?>> gatewayClasses = reflections.getTypesAnnotatedWith(Gateway.class);
        if (gatewayClasses.isEmpty()) {
            log.info("No @Gateway-annotated classes found in the package. SKIP processGateways");
            return;
        }
        // Collect all Gateway values
        Set<String> gatewayValues = new java.util.HashSet<>();
        for (Class<?> cls : gatewayClasses) {
            Gateway gw = cls.getAnnotation(Gateway.class);
            if (gw != null) {
                gatewayValues.add(gw.value());
            }
        }
        if (gatewayValues.size() > 1) {
            StringBuilder classNames = new StringBuilder();
            for (Class<?> cls : gatewayClasses) {
                classNames.append(cls.getName()).append(", ");
            }
            throw new IllegalStateException("Multiple @Gateway-annotated classes with different values found: " 
                + gatewayValues + ". Classes: " + classNames.toString());
        } else if (gatewayClasses.size() > 1) {
            StringBuilder classNames = new StringBuilder();
            for (Class<?> cls : gatewayClasses) {
                classNames.append(cls.getName()).append(", ");
            }
            log.warn("Multiple @Gateway-annotated classes found with the same value: " + gatewayValues.iterator().next() + ". Classes: " + classNames.toString());
        }
        
        Class<?> clazz = gatewayClasses.iterator().next();
        Gateway gateway = clazz.getAnnotation(Gateway.class);
        String gatewayPrefix = gateway.value();
        if (gatewayPrefix == null || gatewayPrefix.isEmpty()) {
            throw new IllegalArgumentException("@Gateway url value is mandatory and must not be empty");
        }
        String classPath = "";
        Path classPathAnn = clazz.getAnnotation(Path.class);
        if (classPathAnn != null) {
            classPath = classPathAnn.value();
        }
        Client client = ClientBuilder.newClient();
        for (Method method : clazz.getDeclaredMethods()) {
            Path methodPathAnn = method.getAnnotation(Path.class);
            if (methodPathAnn != null) {
                String methodPath = methodPathAnn.value();
                String url = combinePaths(classPath, methodPath);
                RegistryUrlDto dto = new RegistryUrlDto();
                dto.setApplication(gatewayPrefix);
                dto.setHost(gatewayPrefix);
                dto.setUrl(url);
                log.info("Registrate {}", dto);
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
