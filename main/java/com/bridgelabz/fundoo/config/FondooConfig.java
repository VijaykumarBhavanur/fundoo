package com.bridgelabz.fundoo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bridgelabz.fundoo.note.model.Label;
@Configuration
public class FondooConfig {
	@Bean
	public ModelMapper modelMapper() 
	{
	    return new ModelMapper();
	}
	
	@Bean
	public BCryptPasswordEncoder bcyBCryptPasswordEncoder() 
	{
	    return new BCryptPasswordEncoder();
	}
	
}
