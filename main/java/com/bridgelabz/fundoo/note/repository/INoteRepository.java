package com.bridgelabz.fundoo.note.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;

@Repository
public interface INoteRepository extends MongoRepository<Note, String> {
	public List<Note> findByIsPinnedAndEmailId(boolean pin, String emailId);

	public List<Note> findByIsArchievedAndEmailId(boolean archive, String emailId);

	public List<Note> findByIsTrashedAndEmailId(boolean trash, String emailId);

	public void deleteByIsTrashedAndEmailId(boolean trash, String emailId);

	public Note findByIdAndEmailId(String id, String emailId);

	public List<Note> findByEmailId(String emailId);
}
