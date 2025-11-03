package com.parkingsystem.parkingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                String reactAppUrl = "http://localhost:5173";

                registry.addMapping("/api/v1/parking/**") // Sadece bu URL kalıbına izin ver
                        .allowedOrigins(reactAppUrl) // Sadece React uygulamamızdan gelen isteklere izin ver
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Bu metotlara izin ver
                        .allowedHeaders("*"); // Tüm header'lara izin ver
            }
        };
    }
}