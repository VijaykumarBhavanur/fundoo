package com.bridgelabz.fundoo.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {


	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private IRegisterRepository userRepository;


	@Mock
	private JavaMailSender javaMailSender;

	@Mock
	private ModelMapper modelmapper;

	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	LoginException loginException;
	

	RegisterUser user = new RegisterUser("1", "user@gmail.com", "user1", "9164480832", "user@123", null, null, null, false);

	String email="test@gmail.com";
	String password="test!123";
	@Test
	public void testvalidateCredentials() throws Exception
	{
		when(userRepository.findByEmailId(email)).thenReturn(user);
		when(bCryptPasswordEncoder.matches(password,password)).thenReturn(true);
		when(email.isEmpty()).thenThrow(LoginException.class);
		when(password.isEmpty()).thenThrow(LoginException.class);
		Response response=userServiceImpl.validateCredentials(email, password);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	

}