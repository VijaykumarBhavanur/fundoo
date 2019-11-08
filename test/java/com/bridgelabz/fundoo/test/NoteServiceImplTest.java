package com.bridgelabz.fundoo.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.repository.ILabelRepository;
import com.bridgelabz.fundoo.note.repository.INoteRepository;
import com.bridgelabz.fundoo.note.service.NoteServiceImpl;
import com.bridgelabz.fundoo.note.util.ENUM;
import com.bridgelabz.fundoo.responseentity.Response;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class NoteServiceImplTest {

	@InjectMocks
	NoteServiceImpl service;

	@Mock
	ModelMapper mapper;
	@Mock
	private INoteRepository repository;
	
	@Mock
	private ILabelRepository labelRepository;

	String email = "user@gmail.com";
	String noteId = "dsfsf!@##";
	List<Note> noteList = new ArrayList<Note>();
	Note note = new Note();
	Optional<Label> label= Optional.of(new Label());
	Label label1=new Label();
	@Test
	public void testCreateNote() throws Exception {

		NoteDTO dto = new NoteDTO();

		dto.setTitle("title");
		dto.setDescription("description");
		String email = "user@gmail.com";

		// Definition of mock object
		when(mapper.map(dto, Note.class)).thenReturn(note);
		when(repository.save(note)).thenReturn(note);

		Response response = service.createNote(dto, email);
		System.out.println("::::::::::::::::::testCreateNote Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testdeleteNote() throws Exception {

		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		
		doNothing().when(repository).deleteById(noteId);

		Response response = service.deleteNote(noteId, email);
		System.out.println("::::::::::::::::::testdeleteNote Response:::::::::\n" + response + "\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testgetAllNote() throws Exception {
		noteList.add(new Note());
		noteList.add(new Note());
		when(repository.findByEmailId(email)).thenReturn(noteList);
		Response response = service.getAllNote(email);
		System.out.println("::::::::::::::::::testgetAllNote Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	public void testpinNote() throws Exception {
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		when(repository.save(note)).thenReturn(note);

		Response response = service.pinNote(noteId, email);
		System.out.println("::::::::::::::::::testpinNote Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testgetPinnedNotes() throws Exception {
		when(repository.findByIsPinnedAndEmailId(true, email)).thenReturn(noteList);
		Response response = service.getPinnedNotes(email);
		System.out.println("::::::::::::::::::testgetPinnedNotes Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testupdateNote() throws Exception {

		NoteDTO dto = new NoteDTO();
		

		dto.setTitle("new title");
		dto.setDescription("new description");
		String email = "user@gmail.com";

		// Definition of mock object
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		when(repository.save(note)).thenReturn(note);

		Response response = service.updateNote(noteId, dto, email);
		System.out.println("::::::::::::::::::testupdateNote Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testsortNoteByName() throws Exception {
		// Definition of mock object
		when(repository.findByEmailId(email)).thenReturn(noteList);
		Response response = service.sortNoteByName(email);
		System.out.println("::::::::::::::::::testsortNoteByName Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testaddCollabarator() throws Exception {
		// Definition of mock object
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		when(repository.save(note)).thenReturn(note);
		Response response = service.addCollabarator(noteId, email, email);
		System.out.println("::::::::::::::::::testaddCollabarator Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testgetAllCollabarators() throws Exception {
		// Definition of mock object
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		Response response = service.getAllCollabarators(noteId, email);
		System.out.println("::::::::::::::::::testgetAllCollabarators Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testaddLabelToNote() throws Exception {
		// Definition of mock object
		String labelId="kjsbdd54d566s54^&&^$";
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		when(labelRepository.findById(labelId)).thenReturn(label);
		when(repository.save(note)).thenReturn(note);
		when(labelRepository.save(label1)).thenReturn(label1);
		Response response = service.addLabelToNote(noteId, labelId, email);
		System.out.println("::::::::::::::::::testaddLabelToNote Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testaddRemainder() throws Exception {
		// Definition of mock object
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		when(repository.save(note)).thenReturn(note);
		Response response = service.addRemainder(LocalDateTime.now(), noteId, email,ENUM.DAILY);
		System.out.println("::::::::::::::::::testaddRemainder Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	public void testdeleteRemainder() throws Exception {
		// Definition of mock object
		when(repository.findByIdAndEmailId(noteId, email)).thenReturn(note);
		when(repository.save(note)).thenReturn(note);
		Response response = service.deleteRemainder(noteId, email);
		System.out.println("::::::::::::::::::testdeleteRemainder Response:::::::::\n" + response + "\n\n\n\n");
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	

}
