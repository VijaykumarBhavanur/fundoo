package com.bridgelabz.fundoonote.service;

import java.util.List;

import com.bridgelabz.fundoonote.dto.NoteDTO;
import com.bridgelabz.fundoonote.model.Note;

public interface INoteService
{
	public String createNote(NoteDTO noteDto);
	public String deleteNote(String noteId);
	public String updateNote(String noteId,NoteDTO note);
	public List<Note> getAllNote();
	public String pinNote(String id);
	public String unPinNote(String id);
	public String archieveNote(String id);
	public String unArchieveNote(String id);
	public String trashNote(String id);
	public String restoreNote(String id);
	
	public List<Note>getPinnedNotes();
	public List<Note> getArchievedNotes();
	public List<Note> getTrashedNotes();
}
