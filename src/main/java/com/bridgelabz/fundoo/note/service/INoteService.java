package com.bridgelabz.fundoo.note.service;

import java.time.LocalDateTime;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.util.ENUM;
import com.bridgelabz.fundoo.responseentity.Response;

public interface INoteService {
	public Response createNote(NoteDTO noteDto, String token);

	public Response deleteNote(String noteId, String token);

	public Response updateNote(String noteId, NoteDTO note, String token);

	public Response getAllNote(String token);

	public Response pinNote(String id, String token);

	public Response archieveNote(String id, String token);

	public Response trashNote(String id, String token);

	public Response getPinnedNotes(String token);

	public Response getArchievedNotes(String token);

	public Response getTrashedNotes(String token);

	public Response sortNoteByName(String token);

	public Response sortNoteByEditedDate(String token);

	public Response addCollabarator(String noteId, String collabaratorEmail, String token);

	public Response getAllCollabarators(String noteId, String token);

	public Response addLabelToNote(String noteId, String labelId, String token);

	public Response addRemainder(LocalDateTime dateTime, String noteId, String email, ENUM repeat);

	public Response updateRemainder(LocalDateTime dateTime, String noteId, String email, ENUM repeat);

	public Response deleteRemainder(String noteId, String email);
}
