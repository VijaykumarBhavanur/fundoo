package com.bridgelabz.fundoo.note.service;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;

@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	ModelMapper modelMapper;
	@Autowired
	INoteRepository repository;

	@Autowired
	ILabelRepository labelRepository;

	@Override
	public String createNote(NoteDTO noteDto, String email) {
		try {

			Note newNote = modelMapper.map(noteDto, Note.class);
			newNote.setEmailId(email);
			newNote.setCreatedAt(java.util.Calendar.getInstance().getTime());
			newNote.setEditedAt(java.util.Calendar.getInstance().getTime());
			repository.save(newNote);
		} catch (Exception e) {
			return "Failed to create new note...";
		}

		return "new note created successfully.....";
	}

	@Override
	public String deleteNote(String noteId) {
		Note note = repository.findById(noteId).get();
		try {
			if (note != null) {
				repository.deleteById(noteId);
				return "Note deleted successfully....";
			}

			else
				return "Note with given id doesn't exist";
		} catch (Exception e) {
			return "Failed to delete note";
		}
	}

	@Override
	public List<Note> getAllNote() {
		List<Note> noteList = repository.findAll();

		return noteList;
	}

	@Override
	public String pinNote(String id) {
		Note notefromdb = repository.findById(id).get();
		if (notefromdb != null) {
			notefromdb.setPinned(true);
			repository.save(notefromdb);
			return "note pinned successfully....";
		} else
			return "No note exist with given id";
	}

	@Override
	public String unPinNote(String id) {
		Note notefromdb = repository.findById(id).get();
		if (notefromdb != null) {
			notefromdb.setPinned(false);
			repository.save(notefromdb);
			return "note un-pinned successfully....";
		} else
			return "No note exist with given id";

	}

	@Override
	public String archieveNote(String id) {

		Note notefromdb = repository.findById(id).get();
		if (notefromdb != null) {
			notefromdb.setArchieved(true);

			repository.save(notefromdb);
			return "note archived successfully....";
		} else
			return "No note exist with given id";
	}

	@Override
	public String unArchieveNote(String id) {

		Note notefromdb = repository.findById(id).get();
		if (notefromdb != null) {
			notefromdb.setArchieved(false);
			repository.save(notefromdb);
			return "note un-archived successfully....";
		} else
			return "No note exist with given id";

	}

	@Override
	public List<Note> getPinnedNotes() {
		return repository.findByIsPinned(true);
	}

	@Override
	public List<Note> getArchievedNotes() {
		return repository.findByIsArchieved(true);

	}

	@Override
	public List<Note> getTrashedNotes() {
		return repository.findByIsTrashed(true);

	}

	@Override
	public String trashNote(String id) {
		Note notefromdb = repository.findById(id).get();
		if (notefromdb != null) {
			notefromdb.setTrashed(true);
			notefromdb.setPinned(false);
			notefromdb.setArchieved(false);
			repository.save(notefromdb);
			return "note trashed successfully....";
		} else
			return "No note exist with given id";
	}

	@Override
	public String restoreNote(String id) {
		Note notefromdb = repository.findById(id).get();
		if (notefromdb != null) {
			notefromdb.setTrashed(false);
			notefromdb.setArchieved(false);
			notefromdb.setPinned(false);
			repository.save(notefromdb);
			return "note restored successfully....";
		} else
			return "No note exist with given id";
	}

	@Override
	public String updateNote(String noteId, NoteDTO note) {
		Note notefromdb = repository.findById(noteId).get();
		if (notefromdb != null) {
			if (!note.getTitle().isEmpty()) {
				notefromdb.setTitle(note.getTitle());
			}

			if (!note.getDescription().isEmpty()) {
				notefromdb.setDescription(note.getDescription());
			}

			notefromdb.setEditedAt(new Date());
			repository.save(notefromdb);
			return "note updated successfully....";
		} else
			return "No note exist with given id";

	}

	@Override
	public List<Note> sortNoteByName() {
		List<Note> noteList = getAllNote();
		noteList.sort((Note n1, Note n2) -> n1.getTitle().compareTo(n2.getTitle()));
		return noteList;
	}

	@Override
	public List<Note> sortNoteByEditedDate() {
		List<Note> noteList = getAllNote();
		noteList.sort((Note n1, Note n2) -> n1.getCreatedAt().compareTo(n2.getCreatedAt()));
		return noteList;
	}

	@Override
	public boolean addCollabarator(String noteId, String collabaratorEmail) {
		Note note = repository.findById(noteId).get();
		if (note == null)
			return false;

		else {
			note.getCollabList().add(collabaratorEmail);
			repository.save(note);
		}
		return true;
	}

	@Override
	public List<String> getAllCollabarators(String noteId) {
		Note note = repository.findById(noteId).get();
		if (note != null)
			return repository.findById(noteId).get().getCollabList();
		else
			return null;
	}

	@Override
	public String addLabelToNote(String noteId, String labelId) {
		System.out.println("Recieved in service noteId:\n" + noteId + "\nLabelId: " + labelId);

		System.out.println("No. Of records: " + repository.count());

		Note note = repository.findById(noteId).get();
		System.out.println("Found note by id: " + note);
		if (note == null)
			return "Invalid noteId";

		Label label = labelRepository.findById(labelId).get();
		System.out.println("Found label by id: " + label);

		if (label == null)
			return "Invalid labelId";

		System.out.println("Adding label to note:::::::::::");
		note.getLabelList().add(label);

		System.out.println("Adding note to label:::::::::::");
		label.getLabeledNotes().add(note);

		System.out.println("Saving changes:::::::::");
		repository.save(note);
		labelRepository.save(label);

		return "success";
	}

}
