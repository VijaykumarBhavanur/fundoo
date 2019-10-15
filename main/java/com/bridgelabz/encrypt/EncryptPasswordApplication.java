package com.bridgelabz.encrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackages = {"com.bridgelabz.encrypt"})
@EntityScan("com.bridgelabz.encrypt.model")
@EnableMongoRepositories("com.bridgelabz.encrypt.repository")

public class EncryptPasswordApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncryptPasswordApplication.class, args);
	}

}
