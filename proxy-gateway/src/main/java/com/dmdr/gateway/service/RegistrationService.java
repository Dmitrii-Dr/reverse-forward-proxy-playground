package com.dmdr.gateway.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import com.dmdr.gateway.model.RegistryUrlDto;
import com.dmdr.gateway.model.db.RegistryUrlEntity;

import java.time.LocalDateTime;

@ApplicationScoped
public class RegistrationService {
    
    @Transactional
    public void register(RegistryUrlDto request) {
        RegistryUrlEntity entity = new RegistryUrlEntity();
        entity.application = request.getApplication();
        entity.host = request.getHost();
        entity.url = request.getUrl();
        entity.createdAt = LocalDateTime.now();
        entity.persist();
    }
}