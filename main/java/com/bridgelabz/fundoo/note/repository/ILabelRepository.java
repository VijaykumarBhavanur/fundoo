package com.bridgelabz.fundoo.note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Label;
@Repository
public interface ILabelRepository extends MongoRepository<Label,String> 
{
	public List<Label> findByEmailId(String emailId);
	public List<Label> findAll();
}
