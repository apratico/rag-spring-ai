package com.apratico.ragspringai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RagSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RagSpringAiApplication.class, args);
    }
}
