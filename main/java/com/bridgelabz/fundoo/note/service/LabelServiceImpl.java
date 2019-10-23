package com.bridgelabz.fundoo.note.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;

@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	INoteRepository noteRepository;

	@Autowired
	ILabelRepository repository;

	@Override
	public String createLabel(String emailId, String labelName) {
		Label label = new Label();
		label.setLabelName(labelName);
		label.setEmailId(emailId);
		repository.save(label);
		return "label created successfully....";

	}

	@Override
	public List<Label> getAllLabel() {
		return repository.findAll();
	}

	@Override
	public boolean deleteLabel(String token, String labelId) {
		System.out.println("deleting label in service by id::::::" + labelId);

		try {
			System.out.println("finding label by id in try:::::" + repository.findById(labelId));

			List<Note> noteList = repository.findById(labelId).get().getLabeledNotes();
			for (Note note : noteList) {
				noteRepository.findById(note.getId()).get().getLabelList().remove(repository.findById(labelId).get());
				noteRepository.save(note);
			}

			if (repository.findById(labelId).get() != null) {
				System.out.println("finding label by id:::::" + repository.findById(labelId));
				repository.deleteById(labelId);

				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println("In catch..................\n" + e.getMessage());
		}

		System.out.println("returning false");
		return false;
	}

	@Override
	public String addNote(String noteId, String labelId) {
		Note note = noteRepository.findById(noteId).get();
		System.out.println("found note by id::::\n" + note);
		if (note == null)
			return "Invalid note";

		Label label = repository.findById(labelId).get();
		System.out.println("found label by id::::\n" + label);
		if (label == null)
			return "Invalid label";

		label.getLabeledNotes().add(note);
		// adding label to note
		note.getLabelList().add(label);
		repository.save(label);
		noteRepository.save(note);
		return "success";

	}

	@Override
	public List<Label> getAllLabelByUser(String email) {
		return repository.findByEmailId(email);
	}

	@Override
	public boolean renameLabel(String labelId, String newLabelName) {
		Label labelFromDb = repository.findById(labelId).get();
		if (labelFromDb == null)
			return false;

		labelFromDb.setLabelName(newLabelName);
		repository.save(labelFromDb);
		return true;

	}

}
