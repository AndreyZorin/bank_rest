package com.example.bankcards.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Свойства CORS.
 */
@Getter
@ConfigurationProperties(prefix = "spring.cors.url")
public class UrlBasedCorsConfigurationProperties {

    private Map<String, CorsConfiguration> configurations = new LinkedHashMap<>();

    public void setConfigurations(LinkedHashMap<String, CorsConfiguration> configurations) {
        this.configurations = configurations;
    }
}
