package com.bridgelabz.fundoo.note.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.service.ILabelService;
import com.bridgelabz.fundoo.responseentity.Response;

@RestController
@RequestMapping("/labels")
public class LabelController {

	@Autowired
	private ILabelService labelService;

	@PostMapping("/label")
	public ResponseEntity<Response> createLabel(@RequestHeader String emailId, @RequestParam String labelName) {
		return new ResponseEntity<Response>(labelService.createLabel(emailId, labelName), HttpStatus.OK);
	}

	@GetMapping("/labels/By/User")
	public ResponseEntity<Response> getAllLabelByUser(@RequestParam String email) {
		return new ResponseEntity<Response>(labelService.getAllLabelByUser(email), HttpStatus.OK);
	}

	@PutMapping("/label")
	public ResponseEntity<Response> updateLabel(@RequestParam String labelId, @RequestParam String newLabelName) {
		return new ResponseEntity<Response>(labelService.renameLabel(labelId, newLabelName), HttpStatus.OK);

	}

	@DeleteMapping("/label")
	public ResponseEntity<Response> deleteLabel(@RequestParam String labelId, @RequestHeader String token) {
		return new ResponseEntity<Response>(labelService.deleteLabel(token, labelId), HttpStatus.OK);

	}
}
