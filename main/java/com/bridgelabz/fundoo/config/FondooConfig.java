package com.bridgelabz.fundoo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class FondooConfig {
	@Bean
	public ModelMapper modelMapper() 
	{
	    return new ModelMapper();
	}
}
