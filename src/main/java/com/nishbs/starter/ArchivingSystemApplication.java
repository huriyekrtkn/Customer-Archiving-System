package com.nishbs.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.nishbs"})
@ComponentScan(basePackages = {"com.nishbs"})
@EnableJpaRepositories(basePackages = {"com.nishbs"})
public class ArchivingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchivingSystemApplication.class, args);
	}

}