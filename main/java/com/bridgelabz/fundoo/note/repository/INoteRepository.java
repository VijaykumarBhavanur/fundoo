package com.bridgelabz.fundoo.note.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.note.model.Note;

@Repository
public interface INoteRepository extends MongoRepository<Note, String> {
	public List<Note> findByIsPinned(boolean pin);

	public List<Note> findByIsArchieved(boolean archive);

	public List<Note> findByIsTrashed(boolean trash);

	public void deleteByIsTrashed(boolean trash);
}
