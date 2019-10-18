package com.bridgelabz.fundoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"com.bridgelabz.fundoo.config,com.bridgelabz.fundoonote"})
@EnableMongoRepositories("com.bridgelabz.fundoonote.repository")
/*
 * @EntityScan("com.bridgelabz.fundoo.model")
 * 
 * @EnableMongoRepositories("com.bridgelabz.fundoo.repository")
 */

public class FundooApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundooApplication.class, args);
	}

}
