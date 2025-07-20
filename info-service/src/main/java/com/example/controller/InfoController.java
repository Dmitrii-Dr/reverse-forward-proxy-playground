package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dmdr.gateway.model.annotation.Gateway;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.ws.rs.Path;

@RestController
@Gateway("info-service")
public class InfoController {

    @GetMapping("/info")
    @Path("/info")
    public String getInfo() {
        // Add current timestamp to log
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);
        System.out.println("Current Timestamp: " + formattedTime);

        return formattedTime + ": Info Service is running";
    }

    @GetMapping("/customer-info")
    @Path("/customer-info")
    public String getCustomerInfo() {
        // Call proxy-gateway to get customer info from CustomerResource
        String proxyGatewayUrl = "http://proxy-gateway:8080/customer-service/customers";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(proxyGatewayUrl, String.class);
        return result;
    }
}