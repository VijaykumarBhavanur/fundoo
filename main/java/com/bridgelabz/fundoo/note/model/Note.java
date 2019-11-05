package com.bridgelabz.fundoo.note.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bridgelabz.fundoo.note.util.ENUM;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
public class Note {
	@Id
	private String id;
	private String emailId;
	private String title;
	private String description;
	private Date createdAt;
	private Date editedAt;
	private boolean isPinned;
	private boolean isArchieved;
	private boolean isTrashed;
	private LocalDateTime remainder;
	private ENUM repeat;
	private List<String> collabList = new ArrayList<>();
	@DBRef(lazy = true)
	private List<String> labelList = new ArrayList<>();
	
	public Note() {
	}
	
}
