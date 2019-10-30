package com.bridgelabz.fundoo.note.service;

import com.bridgelabz.fundoo.responseentity.Response;

public interface ILabelService {

	public Response createLabel(String token, String labelName);

	public Response getAllLabel();

	public Response deleteLabel(String token, String labelId);

	public Response getAllLabelByUser(String email);

	public Response renameLabel(String labelId, String newLabelName);
}
