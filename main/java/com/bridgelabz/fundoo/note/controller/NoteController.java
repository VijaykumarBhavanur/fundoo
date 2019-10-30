package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.service.INoteService;
import com.bridgelabz.fundoo.responseentity.Response;

@RestController
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private INoteService noteService;

	@PostMapping("/note")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDto, @RequestHeader String token) {
		if (noteDto.getTitle().contentEquals("") && noteDto.getDescription().contentEquals(""))
			return new ResponseEntity<Response>(new Response(200, null, "Title & description both can't be empty!!"),
					HttpStatus.OK);

		return new ResponseEntity<Response>(noteService.createNote(noteDto, token), HttpStatus.OK);
	}

	@DeleteMapping("/note")
	public ResponseEntity<Response> deleteNote(@RequestParam String id, @RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.deleteNote(id, token), HttpStatus.OK);
	}

	@PutMapping("/note")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDto, @RequestParam String noteId,
			@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.updateNote(noteId, noteDto, token), HttpStatus.OK);
	}

	@GetMapping("/notes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.getAllNote(token), HttpStatus.OK);
	}

	@PutMapping("/pin/note")
	public ResponseEntity<Response> pinNote(@RequestParam String id,@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.pinNote(id,token), HttpStatus.OK);
	}

	@GetMapping("/pin/notes")
	public ResponseEntity<Response> getAllPinnedNotes(@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.getPinnedNotes(token), HttpStatus.OK);
	}

	@PutMapping("/archieve/note")
	public ResponseEntity<Response> archieveNote(@RequestParam String id,@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.archieveNote(id, token), HttpStatus.OK);
	}

	@PutMapping("/trash/note")
	public ResponseEntity<Response> trashNote(@RequestParam String id,@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.trashNote(id, token), HttpStatus.OK);
	}

	@GetMapping("/archieved/notes")
	public ResponseEntity<Response> getAllArchievedNotes(@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.getArchievedNotes(token), HttpStatus.OK);
	}

	@GetMapping("/trashed/notes")
	public ResponseEntity<Response> getAllTrashedNotes(@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.getTrashedNotes(token), HttpStatus.OK);
	}

	@GetMapping("/note/sorted/by/name")
	public ResponseEntity<Response> getSortedNotesByName(@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.sortNoteByName(token), HttpStatus.OK);
	}

	@GetMapping("/note/sorted/by/edited/date")
	public ResponseEntity<Response> getSortedNotesByEditedDate(@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.sortNoteByEditedDate(token), HttpStatus.OK);
	}

	@PutMapping("/collabarartor")
	public ResponseEntity<Response> addCollabaratorToNote(@RequestParam String noteId,
			@RequestParam String collabaratorEmail,@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.addCollabarator(noteId, collabaratorEmail, token),
				HttpStatus.OK);
	}

	@GetMapping("/note/collabarators")
	public ResponseEntity<Response> getAllCollabarators(@RequestParam String noteId,@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.getAllCollabarators(noteId, token), HttpStatus.OK);
	}

	@PutMapping("/label")
	public ResponseEntity<Response> addLabelToNote(@RequestParam String noteId, @RequestParam String labelId,
			@RequestHeader String token) {
		return new ResponseEntity<Response>(noteService.addLabelToNote(noteId, labelId, token), HttpStatus.OK);
	}
}
