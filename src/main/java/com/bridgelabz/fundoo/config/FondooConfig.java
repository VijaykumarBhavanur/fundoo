package com.bridgelabz.fundoo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
/*import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;*/
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
//@EnableRedisRepositories
public class FondooConfig {

	/**
	 * Purpose to return ObjectMapper Bean
	 * 
	 * @return ObjectMapper Bean
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * Purpose to return BCryptPasswordEncoder Bean
	 * 
	 * @return BCryptPasswordEncoder Bean
	 */
	@Bean
	public BCryptPasswordEncoder bcyBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Purpose to return Docket Bean to configure swagger
	 * 
	 * @return Docket Bean
	 */

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.fundoo")).build();
	}

	/*
	 * @Bean public JedisConnectionFactory jedisConnectionFactory() {
	 * RedisProperties properties = redisProperties(); RedisStandaloneConfiguration
	 * configuration = new RedisStandaloneConfiguration();
	 * configuration.setHostName(properties.getHost());
	 * configuration.setPort(properties.getPort());
	 * 
	 * return new JedisConnectionFactory(configuration); }
	 * 
	 * @Bean public RedisTemplate<String, Object> redisTemplate() { final
	 * RedisTemplate<String, Object> template = new RedisTemplate<>();
	 * template.setConnectionFactory(jedisConnectionFactory());
	 * template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
	 * return template; }
	 * 
	 * @Bean
	 * 
	 * @Primary public RedisProperties redisProperties() { return new
	 * RedisProperties(); }
	 */
}
