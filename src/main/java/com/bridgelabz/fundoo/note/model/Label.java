package com.bridgelabz.fundoo.note.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Document(collection = "label")
@AllArgsConstructor
public class Label {
	@Id
	private String labelId;
	private String emailId;
	private String labelName;
	@DBRef(lazy = true)
	private List<Note> labeledNotes = new ArrayList<>();
	public Label() {
	
	}
}
