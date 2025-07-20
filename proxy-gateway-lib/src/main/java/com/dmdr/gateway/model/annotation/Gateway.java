package com.dmdr.gateway.model.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Custom annotation to mark resource classes for gateway registration.
 * <p>
 * The value is a mandatory prefix for all registered paths of the resource.
 * Example: @Gateway("customer-service") will register all paths as /customer-service/your-path
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Gateway {
    /**
     * Mandatory prefix for all resource paths. Example: "customer-service".
     */
    String value();
}
