package com.bridgelabz.fundoo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.fundoo.model.RegisterUser;

public interface IRegisterRepository extends MongoRepository<RegisterUser, String> {
	public RegisterUser findByMobile(String mobile);

	public RegisterUser findByEmailId(String emailId);

}
