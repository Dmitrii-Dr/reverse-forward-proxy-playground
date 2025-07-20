package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//TODO replace with spring.factory file
@ComponentScan({"com.example","com.dmdr.gateway"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}