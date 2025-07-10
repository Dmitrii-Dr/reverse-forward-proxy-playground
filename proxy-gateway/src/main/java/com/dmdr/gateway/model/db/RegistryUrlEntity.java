package com.dmdr.gateway.model.db;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "registry_url")
public class RegistryUrlEntity extends PanacheEntity {
    @Column(nullable = false)
    public String application;

    @Column(nullable = false)
    public String host;

    @Column(nullable = false)
    public String url;

    @Column(name = "created_at", nullable = false)
    public LocalDateTime createdAt;
}
