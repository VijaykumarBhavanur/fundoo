package com.bridgelabz.fundoo.note.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.bridgelabz.fundoo.note.util.ENUM;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.util.TokenUtil;

@RestController
@RequestMapping("/notes")
public class NoteController {
	@Autowired
	private INoteService noteService;

	@PostMapping("/note")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO noteDto, @RequestHeader String token) {
		if (noteDto.getTitle().contentEquals("") && noteDto.getDescription().contentEquals(""))
			return new ResponseEntity<>(new Response(HttpStatus.OK, null, "Title & description both can't be empty!!"),
					HttpStatus.OK);

		Response response = noteService.createNote(noteDto, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@DeleteMapping("/note")
	public ResponseEntity<Response> deleteNote(@RequestParam String id, @RequestHeader String token) {
		Response response = noteService.deleteNote(id, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/note")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDTO noteDto, @RequestParam String noteId,
			@RequestHeader String token) {
		Response response = noteService.updateNote(noteId, noteDto, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@GetMapping("/notes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token) {
		Response response = noteService.getAllNote(TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/pin/note")
	public ResponseEntity<Response> pinNote(@RequestParam String id, @RequestHeader String token) {

		Response response = noteService.pinNote(id, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());

	}

	@GetMapping("/pin/notes")
	public ResponseEntity<Response> getAllPinnedNotes(@RequestHeader String token) {

		Response response = noteService.getPinnedNotes(TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/archieve/note")
	public ResponseEntity<Response> archieveNote(@RequestParam String id, @RequestHeader String token) {
		Response response = noteService.archieveNote(id, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/trash/note")
	public ResponseEntity<Response> trashNote(@RequestParam String id, @RequestHeader String token) {
		Response response = noteService.trashNote(id, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@GetMapping("/archieved/notes")
	public ResponseEntity<Response> getAllArchievedNotes(@RequestHeader String token) {
		Response response = noteService.getArchievedNotes(TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@GetMapping("/trashed/notes")
	public ResponseEntity<Response> getAllTrashedNotes(@RequestHeader String token) {
		Response response = noteService.getTrashedNotes(TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@GetMapping("/note/sorted/by/name")
	public ResponseEntity<Response> getSortedNotesByName(@RequestHeader String token) {

		Response response = noteService.sortNoteByName(TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@GetMapping("/note/sorted/by/edited/date")
	public ResponseEntity<Response> getSortedNotesByEditedDate(@RequestHeader String token) {
		Response response = noteService.sortNoteByEditedDate(TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/collabarartor")
	public ResponseEntity<Response> addCollabaratorToNote(@RequestParam String noteId,
			@RequestParam String collabaratorEmail, @RequestHeader String token) {
		Response response = noteService.addCollabarator(noteId, collabaratorEmail, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());

	}

	@GetMapping("/note/collabarators")
	public ResponseEntity<Response> getAllCollabarators(@RequestParam String noteId, @RequestHeader String token) {

		Response response = noteService.getAllCollabarators(noteId, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());

	}

	@PutMapping("/label")
	public ResponseEntity<Response> addLabelToNote(@RequestParam String noteId, @RequestParam String labelId,
			@RequestHeader String token) {
		Response response = noteService.addLabelToNote(noteId, labelId, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());

	}

	@PostMapping("/reminder")
	public ResponseEntity<Response> addreminder(
			@RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
			@RequestParam String noteId, @RequestHeader String token, @RequestParam(name = "repeat") ENUM repeat) {
		Response response = noteService.addRemainder(localDateTime, noteId, TokenUtil.decodeToken(token), repeat);
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	@PutMapping("/remainder")
	public ResponseEntity<Response> updateRemainder(
			@RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
			@RequestParam String noteId, @RequestHeader String token, @RequestParam(name = "repeat") ENUM repeat) {
		Response response = noteService.updateRemainder(localDateTime, noteId, TokenUtil.decodeToken(token), repeat);
		return new ResponseEntity<>(response, response.getStatusCode());

	}

	@DeleteMapping("/remainder")
	public ResponseEntity<Response> deleteRemainder(
			@RequestParam("localDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime,
			@RequestParam String noteId, @RequestHeader String token) {

		Response response = noteService.deleteRemainder(noteId, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());

	}

}
