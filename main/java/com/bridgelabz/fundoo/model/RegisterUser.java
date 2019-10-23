package com.bridgelabz.fundoo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;

import lombok.Data;

@Document(collection = "Register")
@Data
public class RegisterUser {
	@Id
	private String id;
	private String emailId;
	private String name;
	private String mobile;
	private String password;
	private List<Label> labelList;
	private List<Note> noteList;
}
