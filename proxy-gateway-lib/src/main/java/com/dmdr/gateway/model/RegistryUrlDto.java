package com.dmdr.gateway.model;

import java.time.LocalDateTime; 

import lombok.Data;

@Data
public class RegistryUrlDto {
    private String application;
    private String host;
    private String url;
    private LocalDateTime createdAt;

    public RegistryUrlDto() {
        this.createdAt = LocalDateTime.now();
    }
}