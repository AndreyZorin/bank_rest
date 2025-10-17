package com.example.bankcards;

import com.example.bankcards.property.UrlBasedCorsConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(UrlBasedCorsConfigurationProperties.class)
public class BankRestApplication {

	static void main(String[] args) {
		SpringApplication.run(BankRestApplication.class, args);
	}

}
