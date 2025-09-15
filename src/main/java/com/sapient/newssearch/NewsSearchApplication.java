package com.sapient.newssearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
public class NewsSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsSearchApplication.class, args);
	}

}
