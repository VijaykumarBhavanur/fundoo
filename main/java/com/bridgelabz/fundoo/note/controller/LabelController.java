package com.bridgelabz.fundoo.note.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.service.ILabelService;
import com.bridgelabz.fundoo.responseentity.Response;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	ILabelService labelService;

	@PostMapping("/createLabel")
	public Response createLabel(@RequestHeader String emailId, @RequestParam String labelName) {
		labelService.createLabel(emailId, labelName);
		return new Response(200, null, "new label created successfully....");
	}

	@PostMapping("/addNote")
	public Response addNote(@RequestParam String noteId, String labelId) {
		String response = labelService.addNote(noteId, labelId);
		if (response.contentEquals("success"))
			return new Response(200, null, "note added to label successfully....");
		return new Response(404, null, response);
	}

	@GetMapping("/getAllLabelByUser")
	public Response getAllLabelByUser(@RequestParam String email) {
		List<Label> labelList = labelService.getAllLabelByUser(email);
		if (!labelList.isEmpty())
			return new Response(200, labelList, "Fetched labels by email successfully....");
		return new Response(404, null, "no notes found for given user");
	}

	@PutMapping("/renameLabel")
	public Response updateLabel(@RequestParam String labelId, @RequestParam String newLabelName) {
		if (labelService.renameLabel(labelId, newLabelName))
			return new Response(200, null, "Label renamed successfully...");

		return new Response(404, null, "Failed to rename label, Label with given id doesn't exist....");
	}

	@DeleteMapping("/deleteLabel")
	public Response deleteLabel(@RequestParam String labelId, @RequestHeader String token) {
		System.out.println("Recieved label in controller by id::::" + labelId);
		if (labelService.deleteLabel(token, labelId))
			return new Response(200, null, "Label deleted successfully...");

		return new Response(404, null, "No label exist with given id");

	}
}
