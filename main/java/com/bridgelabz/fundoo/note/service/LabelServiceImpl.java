package com.bridgelabz.fundoo.note.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.util.Utility;

@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private INoteRepository noteRepository;

	@Autowired
	private ILabelRepository repository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LabelServiceImpl.class);

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
		return new Response(HttpStatus.OK, null,Utility.NEW_RECORD_CREATED);
	}

	/**
	 * @return Response with List of all labels
	 */
	@Override
	public Response getAllLabel() {
		
		List<Label>labelList=repository.findAll();
		if(labelList!=null)
		  return new Response(HttpStatus.OK,labelList, "List of all labels");
		
		return new Response(HttpStatus.NOT_FOUND,null,Utility.RECORD_NOT_FOUND);
	}

	/**
	 * @param noteId
	 * @param labelId
	 * @return Response with success or failure message
	 */
	@Override
	public Response deleteLabel(String token, String labelId) {

		try {
			List<Note> noteList;
			Optional<Label> label = repository.findById(labelId);
			
			if (label.isPresent())
			{
					noteList = label.get().getLabeledNotes();
					
				for (Note note : noteList) 
				{
					Optional<Note> notefromdb = noteRepository.findById(note.getId());
					if(notefromdb.isPresent())
					{
						notefromdb.get().getLabelList().remove(label.get());
						noteRepository.save(note);
					}
				}
	
					repository.deleteById(labelId);
	
					return new Response(HttpStatus.OK, null,Utility.RECORD_DELETED);
			}
		} catch (Exception e) {
			LOGGER.debug("In catch..................{}\n",e.getMessage());
		}

		return new Response(HttpStatus.NOT_FOUND, null,Utility.RECORD_NOT_FOUND);
	}

	/**
	 * @param email
	 * @return list of labels of given user
	 */
	@Override
	public Response getAllLabelByUser(String email) {
		List<Label>labelList=repository.findByEmailId(email);
		if(labelList!=null)
			return new Response(HttpStatus.OK,labelList, "List of labels of particular user");
		return new Response(HttpStatus.NOT_FOUND,null,Utility.RECORD_NOT_FOUND);
	}

	/**
	 * @param labelId
	 * @param newLabelName
	 * @return response with success or failure message
	 */
	@Override
	public Response renameLabel(String labelId, String newLabelName) {
		Label labelFromDb;
		Optional<Label> label = repository.findById(labelId);
		if (label.isPresent())
			labelFromDb = label.get();
		else
			return new Response(HttpStatus.NOT_FOUND, null,Utility.RECORD_NOT_FOUND);

		labelFromDb.setLabelName(newLabelName);
		repository.save(labelFromDb);
		return new Response(HttpStatus.OK, null,Utility.RECORD_UPDATED);

	}

}
