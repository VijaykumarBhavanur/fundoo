package com.bridgelabz.fundoonote.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Note
{
	@Id
	private String id;
	private String title;
	private String description;
	private Date createdAt;
	private Date editedAt;
	private boolean isPinned;
	private boolean isArchieved;
	private boolean isTrashed;
}
