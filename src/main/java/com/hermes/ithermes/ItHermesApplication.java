package com.hermes.ithermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ItHermesApplication {
	public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";
	public static void main(String[] args) {
		SpringApplication.run(ItHermesApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:5500");
				registry.addMapping("/**").allowedOrigins("http://127.0.0.1:5500");
				registry.addMapping("/**").allowedOrigins("http://127.0.0.1:80");
				registry.addMapping("/**").allowedOrigins("http://localhost:80");
				registry.addMapping("/**").allowedMethods(ALLOWED_METHOD_NAMES.split(","));
			}
		};
	}
}
