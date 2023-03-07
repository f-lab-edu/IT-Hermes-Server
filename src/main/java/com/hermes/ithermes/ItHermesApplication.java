package com.hermes.ithermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ItHermesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItHermesApplication.class, args);
    }
}


