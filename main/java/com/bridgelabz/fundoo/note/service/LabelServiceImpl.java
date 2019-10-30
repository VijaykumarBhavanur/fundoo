package com.bridgelabz.fundoo.note.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;
import com.bridgelabz.fundoo.responseentity.Response;

@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private ILabelRepository repository;

	/**
	 * Method to create new label
	 * 
	 * @param token
	 * @param labelName
	 * @return response object with "success" message
	 */
	@Override
	public Response createLabel(String emailId, String labelName) {
		Label label = new Label();
		label.setLabelName(labelName);
		label.setEmailId(emailId);
		repository.save(label);
		return new Response(200, null, "label created successfully....");
	}

	/**
	 * @return Response with List of all labels
	 */
	@Override
	public Response getAllLabel() {
		return new Response(200, repository.findAll(), "List of all labels");
	}

	/**
	 * @param noteId
	 * @param labelId
	 * @return Response with success or failure message
	 */
	@Override
	public Response deleteLabel(String token, String labelId) {

		try {

			List<Note> noteList = repository.findById(labelId).get().getLabeledNotes();
			for (Note note : noteList) {
				noteRepository.findById(note.getId()).get().getLabelList().remove(repository.findById(labelId).get());
				noteRepository.save(note);
			}

			if (repository.findById(labelId).get() != null) {
				repository.deleteById(labelId);

				return new Response(200, null, "Label deleted successfully");
			}
		} catch (Exception e) {
			System.out.println("In catch..................\n" + e.getMessage());
		}

		return new Response(200, null, "Invalid labelId");
	}

	/**
	 * @param email
	 * @return list of labels of given user
	 */
	@Override
	public Response getAllLabelByUser(String email) {
		return new Response(200, repository.findByEmailId(email), "List of labels of particular user");
	}

	/**
	 * @param labelId
	 * @param newLabelName
	 * @return response with success or failure message
	 */
	@Override
	public Response renameLabel(String labelId, String newLabelName) {
		Label labelFromDb = repository.findById(labelId).get();
		if (labelFromDb == null)
			return new Response(200, null, "Invalid labelId");

		labelFromDb.setLabelName(newLabelName);
		repository.save(labelFromDb);
		return new Response(200, null, "Label renamed successfully...");

	}

}
