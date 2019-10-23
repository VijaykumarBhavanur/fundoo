package com.bridgelabz.fundoo.note.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Note
{
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
	private List<String>collabList;
	@DBRef
	private List<Label>labelList=new ArrayList<Label>();
}
