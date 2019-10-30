package com.bridgelabz.fundoo.note.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.util.TokenUtil;

@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private INoteRepository repository;

	@Autowired
	private ILabelRepository labelRepository;

	@Autowired
	private Environment environment;

	/**
	 * Method to create a new note 
	 * @param noteDto
	 * @param token
	 * @return response object with  "success" or "failure" message
	 */
	@Override
	public Response createNote(NoteDTO noteDto, String token) {
		try {

			Note newNote = modelMapper.map(noteDto, Note.class);
			newNote.setEmailId(TokenUtil.decodeToken(token));
			newNote.setCreatedAt(java.util.Calendar.getInstance().getTime());
			newNote.setEditedAt(java.util.Calendar.getInstance().getTime());
			repository.save(newNote);
		} catch (Exception e) {
			return new Response(400, null, "failed to create note.....");
		}

		return new Response(200, null, "new note created successfully.....");
	}

	/**
	 * Method to delete note permanently
	 * @param noteId
	 * @param token
	 * @return response object with  "success" or "failure" message
	 */
	@Override
	public Response deleteNote(String noteId, String token) {
		Note note = repository.findByIdAndEmailId(noteId, TokenUtil.decodeToken(token));
		try {
			if (note != null) {
				repository.deleteById(noteId);
				return new Response(200, null, "note deleted successfully.....");
			}

			else
				return new Response(400, null, "Note with given id doesn't exist");
		} catch (Exception e) {
			return new Response(400, null, "failed to delete note.....");
		}
	}

	@Override
	public Response getAllNote(String token) {
		return new Response(200, repository.findByEmailId(TokenUtil.decodeToken(token)), "List of notes");
	}

	
	/**
	 * Method to pin or un-pin note
	 * @param token
	 * @return response object with  "success" or "failure" message
	 */
	@Override
	public Response pinNote(String id, String token) {
		Note notefromdb = repository.findByIdAndEmailId(id, TokenUtil.decodeToken(token));

		if (notefromdb == null)
			return new Response(400, environment.getProperty("not exist"), null);

		if (notefromdb.isPinned())
			notefromdb.setPinned(false);
		else
			notefromdb.setPinned(true);
		repository.save(notefromdb);
		return new Response(200, environment.getProperty("update"), null);

	}
	/**
	 * Method to archive or un-archive note
	 * @param token
	 * @return response object with  "success" or "failure" message
	 */

	@Override
	public Response archieveNote(String id, String token) {

		Note notefromdb = repository.findByIdAndEmailId(id, TokenUtil.decodeToken(token));
		if (notefromdb == null)
			return new Response(400, environment.getProperty("not exist"), null);

		if (notefromdb.isArchieved())
			notefromdb.setArchieved(false);
		else
			notefromdb.setArchieved(true);
		repository.save(notefromdb);
		return new Response(200, environment.getProperty("update"), null);
	}

	/**
	 * Method to return list of Pinned notes
	 * @param token
	 * @return response object with  list of Pinned notes
	 */
	@Override
	public Response getPinnedNotes(String token) {
		return new Response(200, repository.findByIsPinnedAndEmailId(true, TokenUtil.decodeToken(token)),
				"List of Pinned notes");
	}

	/**
	 * Method to return list of Archived notes
	 * @param token
	 * @return response object with  list of Archived notes
	 */
	@Override
	public Response getArchievedNotes(String token) {
		return new Response(200, repository.findByIsArchievedAndEmailId(true, TokenUtil.decodeToken(token)),
				"List of Archieved notes");

	}
	
	/**
	 * Method to return list of trashed notes
	 * @param token
	 * @return response object with  list of trashed notes
	 */

	@Override
	public Response getTrashedNotes(String token) {
		return new Response(200, repository.findByIsTrashedAndEmailId(true, TokenUtil.decodeToken(token)),
				"List of Trashed notes");

	}
	
	/**
	 * Method to trash or un-trash note
	 * @param noteId
	 * @param token
	 * @return response object with  "success" or "failure" message
	 */

	@Override
	public Response trashNote(String noteId, String token) {
		Note notefromdb = repository.findByIdAndEmailId(noteId, TokenUtil.decodeToken(token));
		if (notefromdb == null)
			return new Response(400, environment.getProperty("not exist"), null);

		if (notefromdb.isTrashed())
			notefromdb.setTrashed(false);
		else
			notefromdb.setTrashed(true);
		repository.save(notefromdb);
		return new Response(200, environment.getProperty("update"), null);
	}
	
	/**
	 * Method to update title or description of note
	 * @param noteId
	 * @param note
	 * @param token
	 * @return response object with  "success" or "failure" message
	 */
	
	@Override
	public Response updateNote(String noteId, NoteDTO note, String token) {
		Note notefromdb = repository.findByIdAndEmailId(noteId, TokenUtil.decodeToken(token));
		if (notefromdb != null) {
			if (!note.getTitle().isEmpty() || !note.getDescription().isEmpty()) {
				notefromdb.setTitle(note.getTitle());
				notefromdb.setDescription(note.getDescription());
			}
			notefromdb.setEditedAt(new Date());
			repository.save(notefromdb);
			return new Response(200, environment.getProperty("update"), null);
		} else
			return new Response(400, environment.getProperty("not exist"), null);

	}


	/**
	 * Method to return List of notes sorted by name
	 * @param token
	 * @return response object with  list of notes sorted by name
	 */
	@Override
	public Response sortNoteByName(String token) {
		List<Note> noteList = repository.findByEmailId(TokenUtil.decodeToken(token));

		noteList.sort((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle()));
		return new Response(200, noteList, "List of notes sorted by name");
	}

	/**
	 * Method to return List of notes sorted by edited date
	 * @param token
	 * @return response object with  list of notes sorted by edited date
	 */
	
	@Override
	public Response sortNoteByEditedDate(String token) {
		List<Note> noteList = repository.findByEmailId(TokenUtil.decodeToken(token));
		noteList.sort((Note n1, Note n2) -> n1.getCreatedAt().compareTo(n2.getCreatedAt()));
		return new Response(200, noteList, "List of notes sorted by edited date");
	}

	/**
	 * Method to add collaborator to given note
	 * 
	 * @param noteId
	 * @param collabaratorEmail
	 * @param token
	 * @return response object with "success" or failure  message
	 */
	@Override
	public Response addCollabarator(String noteId, String collabaratorEmail, String token) {
		Note note = repository.findByIdAndEmailId(noteId, TokenUtil.decodeToken(token));

		if (note == null)
			return new Response(400, null, "Note not exist");

		else {
			note.getCollabList().add(collabaratorEmail);
			repository.save(note);
		}
		return new Response(200, null, "Added collabarator sucessfully");
	}

	/**
	 * Method to return collaborators of  given note
	 * 
	 * @param noteId
	 * @param token
	 * @return response object with List of collaborators or failure  message
	 */
	@Override
	public Response getAllCollabarators(String noteId, String token) {
		Note note = repository.findByIdAndEmailId(noteId, TokenUtil.decodeToken(token));
		if (note != null)
			return new Response(200, repository.findById(noteId).get().getCollabList(), "List of collabarartors");
		else
			return new Response(400, null, "Collabarator not exist");
	}

	/**
	 * Method to add label to given note
	 * 
	 * @param noteId
	 * @param labelId
	 * @param token
	 * @return response object with "success" or failure  message
	 */
	@Override
	public Response addLabelToNote(String noteId, String labelId, String token) {
		Note note = repository.findByIdAndEmailId(noteId, TokenUtil.decodeToken(token));
		System.out.println("Found note by id: " + note);
		if (note == null)
			return new Response(400, null, "Note not exist");

		Label label = labelRepository.findById(labelId).get();
		System.out.println("Found label by id: " + label);

		if (label == null)
			return new Response(400, null, "Label not exist");

		System.out.println("Adding label to note:::::::::::");
		note.getLabelList().add(label);

		label.getLabeledNotes().add(note);

		repository.save(note);
		labelRepository.save(label);

		return new Response(200, null, "Label added to note");

	}

}
