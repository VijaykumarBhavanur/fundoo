package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.INoteService;
import com.bridgelabz.fundoo.responseentity.Response;

@RestController
@RequestMapping("/note")
public class NoteController {
	@Autowired
	INoteService noteService;

	@PostMapping("/createNote")
	public ResponseEntity<String> createNote(@RequestBody @Valid NoteDTO noteDto, @RequestParam String email) {
		if (noteDto.getTitle().isBlank() && noteDto.getDescription().isBlank())
			return new ResponseEntity<String>("Title and description both can't be empty...", HttpStatus.OK);
		else
			return new ResponseEntity<String>(noteService.createNote(noteDto, email), HttpStatus.OK);
	}

	@DeleteMapping("/deleteNote")
	public ResponseEntity<String> deleteNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.deleteNote(id), HttpStatus.OK);
	}

	@PutMapping("/updateNote")
	public ResponseEntity<String> updateNote(@RequestBody NoteDTO noteDto, @RequestParam String noteId) {
		return new ResponseEntity<String>(noteService.updateNote(noteId, noteDto), HttpStatus.OK);
	}

	@GetMapping("/getAllNote")
	public ResponseEntity<?> getAllNotes() {

		List<Note> noteList = noteService.getAllNote();
		if (!noteList.isEmpty())
			return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);
		else
			return new ResponseEntity<String>("No notes to display...", HttpStatus.OK);
	}

	@PutMapping("/pinNote")
	public ResponseEntity<String> pinNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.pinNote(id), HttpStatus.OK);
	}

	@PutMapping("/unPinNote")
	public ResponseEntity<String> unPinNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.unPinNote(id), HttpStatus.OK);
	}

	@GetMapping("/getAllPinnedNotes")
	public ResponseEntity<?> getAllPinnedNotes() {

		List<Note> noteList = noteService.getPinnedNotes();
		if (!noteList.isEmpty())
			return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);
		else
			return new ResponseEntity<String>("No pinned notes...", HttpStatus.OK);
	}

	@PutMapping("/archieveNote")
	public ResponseEntity<String> archieveNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.archieveNote(id), HttpStatus.OK);
	}

	@PutMapping("/un-archieveNote")
	public ResponseEntity<String> unArchieveNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.unArchieveNote(id), HttpStatus.OK);
	}

	@PutMapping("/trashNote")
	public ResponseEntity<String> trashNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.trashNote(id), HttpStatus.OK);
	}

	@PutMapping("/restoreNote")
	public ResponseEntity<String> restoreNote(@RequestParam String id) {
		return new ResponseEntity<String>(noteService.restoreNote(id), HttpStatus.OK);
	}

	@GetMapping("/getAllArchievedNotes")
	public ResponseEntity<?> getAllArchievedNotes() {

		List<Note> noteList = noteService.getArchievedNotes();
		if (!noteList.isEmpty())
			return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);
		else
			return new ResponseEntity<String>("No archieved notes...", HttpStatus.OK);
	}

	@GetMapping("/getAllTrashedNotes")
	public ResponseEntity<?> getAllTrashedNotes() {

		List<Note> noteList = noteService.getTrashedNotes();
		if (!noteList.isEmpty())
			return new ResponseEntity<List<Note>>(noteList, HttpStatus.OK);
		else
			return new ResponseEntity<String>("No archieved notes...", HttpStatus.OK);
	}

	@GetMapping("/getNotesSortedByName")
	public Response getSortedNotesByName() {
		return new Response(200, noteService.sortNoteByName(), "List of notes sorted by name");
	}

	@GetMapping("/getNotesSortedByEditedDate")
	public Response getSortedNotesByEditedDate() {
		return new Response(200, noteService.sortNoteByEditedDate(), "List of notes sorted by created date");
	}

	@PutMapping("/addCollabrator")
	public Response addCollabaratorToNote(@RequestParam String noteId, @RequestParam String collabaratorEmail) {
		if (noteService.addCollabarator(noteId, collabaratorEmail))
			return new Response(200, null, "Collabarator added successfully....");

		return new Response(404, null, "Invalid noteId");
	}

	@GetMapping("/getAllCollabaratorsOfNote")
	public Response getAllCollabarators(@RequestParam String noteId) {
		List<String> collabList = noteService.getAllCollabarators(noteId);
		if (collabList == null)
			return new Response(407, null, "Invalid noteId.....");
		if (!collabList.isEmpty())
			return new Response(200, collabList, "List of collabarators....");
		else
			return new Response(200, null, "No collabarartors found for given note...");

	}

	@PutMapping("/addLabel")
	public Response addLabelToNote(@RequestParam String noteId, @RequestParam String labelId) {
		System.out.println("Recieved in controller noteId:\n" + noteId + "\nLabelId: " + labelId);
		String response = noteService.addLabelToNote(noteId, labelId);
		if (noteService.addLabelToNote(noteId, labelId).contentEquals("success"))
			return new Response(200, null, "Label added to note successfully");
		else
			return new Response(407, null, response);

	}

}
