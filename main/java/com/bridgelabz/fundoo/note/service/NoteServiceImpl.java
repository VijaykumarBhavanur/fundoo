package com.bridgelabz.fundoo.note.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.RecordNotFoundException;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;
import com.bridgelabz.fundoo.note.util.ENUM;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.util.Utility;

@Service
//@Cacheable
public class NoteServiceImpl implements INoteService,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private INoteRepository repository;

	@Autowired
	private ILabelRepository labelRepository;

	/**
	 * Method to create a new note
	 * 
	 * @param noteDto
	 * @param token
	 * @return response with "OK" status code or "BAD_REQUEST" status code
	 */
	@Override
	public Response createNote(NoteDTO noteDto, String emailId) {
		try {
			Note newNote = modelMapper.map(noteDto, Note.class);
			newNote.setEmailId(emailId);
			newNote.setCreatedAt(java.util.Calendar.getInstance().getTime());
			newNote.setEditedAt(java.util.Calendar.getInstance().getTime());
			repository.save(newNote);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(HttpStatus.BAD_REQUEST, null, Utility.NEW_RECORD_CREATION_FAILED);
		}

		return new Response(HttpStatus.OK, null, Utility.NEW_RECORD_CREATED);
	}

	/**
	 * Method to delete note permanently
	 * 
	 * @param noteId
	 * @param token
	 * @return response with "OK" status code or "NOT_FOUND" status code
	 */
	@Override
	public Response deleteNote(String noteId, String emailId) {
		Note note = repository.findByIdAndEmailId(noteId, emailId);
		try {
			if (note != null) {
				repository.deleteById(noteId);
				return new Response(HttpStatus.OK, null, Utility.RECORD_DELETED);
			}

			else
				return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
		} catch (Exception e) {
			return new Response(HttpStatus.BAD_REQUEST, null, Utility.OPERATION_FAILED);
		}
	}

	/**
	 * MethTo get notes of given user
	 * 
	 * @param token
	 * @return response with list of notes and "OK" status code or "NOT_FOUND"
	 *         status code status code
	 */
	@Override
	//@Cacheable(value = "/allNotes")
	public Response getAllNote(String emailId) {
		List<Note> noteList = repository.findByEmailId(emailId);
		if (!noteList.isEmpty())
			return new Response(HttpStatus.OK, noteList, Utility.RESOURCE_RETURNED);
		return new Response(HttpStatus.NOT_FOUND, noteList, Utility.RECORD_NOT_FOUND);
	}

	/**
	 * Method to pin or un-pin note
	 * 
	 * @param token
	 * @return response with "OK" status code or "NOT_FOUND" status code
	 */
	//@Cacheable(value = "notes", key = "#emailId")
	@Override
	public Response pinNote(String id, String emailId) {
		Note notefromdb = repository.findByIdAndEmailId(id,emailId);

		if (notefromdb == null)
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
		notefromdb.setPinned(!notefromdb.isPinned());
		repository.save(notefromdb);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);

	}

	/**
	 * Method to archive or un-archive note
	 * 
	 * @param token
	 * @return response object with "success" or "failure" message
	 */

	@Override
	public Response archieveNote(String id, String emailId) {

		Note notefromdb = repository.findByIdAndEmailId(id, emailId);
		if (notefromdb == null)
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);

		notefromdb.setArchieved(!notefromdb.isArchieved());
		repository.save(notefromdb);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
	}

	/**
	 * Method to return list of Pinned notes
	 * 
	 * @param token
	 * @return response with list of Pinned notes and "OK" status code or
	 *         "NOT_FOUND" status code
	 */
	@Override
	public Response getPinnedNotes(String emailId) {
		List<Note> pinNotes = repository.findByIsPinnedAndEmailId(true, emailId);
		if (pinNotes != null)
			return new Response(HttpStatus.OK, pinNotes, Utility.RESOURCE_RETURNED);
		return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
	}

	/**
	 * Method to return list of Archived notes
	 * 
	 * @param token
	 * @return response object with list of Archived notes and "OK" status code or
	 *         "NOT_FOUND" status code
	 */
	@Override
	public Response getArchievedNotes(String emailId) {

		List<Note> archNotes = repository.findByIsArchievedAndEmailId(true, emailId);
		if (archNotes != null)
			return new Response(HttpStatus.OK, archNotes, Utility.RESOURCE_RETURNED);
		return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
	}

	/**
	 * Method to return list of trashed notes
	 * 
	 * @param token
	 * @return response object with list of trashed notes "OK" status code or
	 *         "NOT_FOUND" status code
	 */

	@Override
	public Response getTrashedNotes(String emailId) {
		List<Note> trashNotes = repository.findByIsTrashedAndEmailId(true, emailId);
		if (trashNotes != null)
			return new Response(HttpStatus.OK, trashNotes, Utility.RESOURCE_RETURNED);
		return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
	}

	/**
	 * Method to trash or un-trash note
	 * 
	 * @param noteId
	 * @param token
	 * @return response object with "OK" status code or "NOT_FOUND" status code
	 */

	@Override
	public Response trashNote(String noteId, String emailId) {
		Note notefromdb = repository.findByIdAndEmailId(noteId, emailId);
		if (notefromdb == null)
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_UPDATED);

		notefromdb.setTrashed(!notefromdb.isTrashed());
		repository.save(notefromdb);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
	}

	/**
	 * Method to update title or description of note
	 * 
	 * @param noteId
	 * @param note
	 * @param token
	 * @return response object with "OK" status code or "NOT_FOUND" status code
	 */

	@Override
	public Response updateNote(String noteId, NoteDTO note, String emailId) {
		Note notefromdb = repository.findByIdAndEmailId(noteId, emailId);
		if (notefromdb != null) {
			notefromdb.setTitle(note.getTitle());
			notefromdb.setDescription(note.getDescription());
			notefromdb.setEditedAt(new Date());
			repository.save(notefromdb);
			return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
		}
		return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);

	}

	/**
	 * Method to return List of notes sorted by name
	 * 
	 * @param token
	 * @return response object with list of notes sorted by name and "OK" status
	 *         code or "NOT_FOUND" status code
	 */
	@Override
	public Response sortNoteByName(String emailId) {
		List<Note> noteList = repository.findByEmailId(emailId);

		if (noteList != null) {
			noteList.sort((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle()));
			return new Response(HttpStatus.OK, noteList, Utility.RESOURCE_RETURNED);
		}
		return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);

	}

	/**
	 * Method to return List of notes sorted by edited date
	 * 
	 * @param token
	 * @return response object with list of notes sorted by edited date and "OK"
	 *         status code or "NOT_FOUND" status code
	 */

	@Override
	public Response sortNoteByEditedDate(String emailId) {
		List<Note> noteList = repository.findByEmailId(emailId);

		if (noteList != null) {
			noteList.sort((Note n1, Note n2) -> n1.getCreatedAt().compareTo(n2.getCreatedAt()));
			return new Response(HttpStatus.OK, noteList, Utility.RESOURCE_RETURNED);
		}
		return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
	}

	/**
	 * Method to add collaborator to given note
	 * 
	 * @param noteId
	 * @param collabaratorEmail
	 * @param token
	 * @return response with "OK" status code or "NOT_FOUND" status code
	 */
	@Override
	public Response addCollabarator(String noteId, String collabaratorEmail, String emailId) {
		Note note = repository.findByIdAndEmailId(noteId, emailId);

		if (note == null)
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);

		else {
			note.getCollabList().add(collabaratorEmail);
			repository.save(note);
		}
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
	}

	/**
	 * Method to return collaborators of given note
	 * 
	 * @param noteId
	 * @param token
	 * @return response with List of collaborators "OK" status code or "NOT_FOUND"
	 *         status code
	 */
	@Override
	public Response getAllCollabarators(String noteId, String emailId) {

		Note note = repository.findByIdAndEmailId(noteId, emailId);
		if (note != null)
			return new Response(HttpStatus.OK, note.getCollabList(), Utility.RESOURCE_RETURNED);
		else
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);
	}

	/**
	 * Method to add label to given note
	 * 
	 * @param noteId
	 * @param labelId
	 * @param token
	 * @return response with "OK" status code or "NOT_FOUND" status code
	 */
	@Override
	public Response addLabelToNote(String noteId, String labelId, String emailId) {
		Note note = repository.findByIdAndEmailId(noteId, emailId);

		if (note == null)
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);

		Label label = labelRepository.findById(labelId).get();

		if (label == null)
			return new Response(HttpStatus.NOT_FOUND, null, Utility.RECORD_NOT_FOUND);

		note.getLabelList().add(label.getLabelName());

		label.getLabeledNotes().add(note);

		repository.save(note);
		labelRepository.save(label);

		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);

	}

	/**
	 * Method to add remainder to given note
	 * 
	 * @param localDateTime
	 * @param noteId
	 * @param token
	 * @param repeat
	 * @return @return response with "OK" status code or "BAD_REQUEST" status code
	 */
	@Override
	public Response addRemainder(LocalDateTime dateTime, String noteId, String email, ENUM repeat) {
		Note note = repository.findByIdAndEmailId(noteId, email);
		if (note == null)
			throw new RecordNotFoundException("NOTENOTEXIST");

		if (dateTime.compareTo(LocalDateTime.now()) > 0) {
			EnumSet<ENUM> except = EnumSet.of(ENUM.DAILY, ENUM.DOESNOTREPEAT, ENUM.MONTHLY, ENUM.WEEKLY, ENUM.YEARLY);
			if (except.contains(ENUM.valueOf(repeat.name()))) {
				note.setRemainder(dateTime);
				note.setRepeat(repeat);
				repository.save(note);
			}
			return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
		}
		return new Response(HttpStatus.BAD_REQUEST, null, Utility.OPERATION_FAILED);

	}

	/**
	 * Method to update remainder of given note
	 * 
	 * @param localDateTime
	 * @param noteId
	 * @param token
	 * @param repeat
	 * @return response with "OK" status code or "BAD_REQUEST" status code
	 */
	@Override
	public Response updateRemainder(LocalDateTime dateTime, String noteId, String email, ENUM repeat) {
		Note note = repository.findByIdAndEmailId(noteId, email);
		if (note == null)
			throw new RecordNotFoundException("Note doesn't exist");

		if (dateTime.compareTo(LocalDateTime.now()) > 0) {
			note.setRemainder(dateTime);
			repository.save(note);
			return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
		}
		return new Response(HttpStatus.BAD_REQUEST, null, Utility.RECORD_UPDATION_FAILED);

	}

	/**
	 * Purpose: To delete remainder of given note
	 * 
	 * @param noteId
	 * @param token
	 * @return @return response with "OK" status code or RecordNotFoundException
	 */
	@Override
	public Response deleteRemainder(String noteId, String email) {
		Note note = repository.findByIdAndEmailId(noteId, email);
		if (note == null)
			throw new RecordNotFoundException("Note doesn't exist");

		note.setRemainder(null);
		repository.save(note);
		return new Response(HttpStatus.OK, null, Utility.RECORD_DELETED);
	}

}
