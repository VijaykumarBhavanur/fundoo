package com.bridgelabz.fundoo.note.service;

import java.util.List;

import com.bridgelabz.fundoo.note.model.Label;

public interface ILabelService {
	public String createLabel(String token, String labelName);

	public List<Label> getAllLabel();

	public boolean deleteLabel(String token, String labelId);

	public String addNote(String noteId, String labelId);

	public List<Label> getAllLabelByUser(String email);

	public boolean renameLabel(String labelId, String newLabelName);
}
