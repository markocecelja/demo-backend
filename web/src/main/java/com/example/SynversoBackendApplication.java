package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@EntityScan(basePackages = {"com.example"})
@EnableJpaRepositories(basePackages = {"com.example"})
@SpringBootApplication(scanBasePackages = "com.example")
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class SynversoBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SynversoBackendApplication.class, args);
	}

}
