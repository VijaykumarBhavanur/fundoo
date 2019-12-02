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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;
import com.bridgelabz.fundoo.service.UserServiceImpl;
import com.bridgelabz.fundoo.util.TokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleUserTest {

	private MockMvc mockmvc;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	IRegisterRepository userRepository;

	@Mock
	TokenUtil tokenUtil;

	
	

	@Mock
	JavaMailSender javaMailSender;

	@Mock
	ModelMapper modelmapper;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	RegisterDTO userDTO;

	RegisterUser user = new RegisterUser("1", "test@gmail.com", "test", "9164480832", "test@123", null, null, null,
			false);

	@Before
	public void Setup() throws Exception {
		mockmvc = MockMvcBuilders.standaloneSetup(userServiceImpl).build();
	}

	/**
	 * to test register api
	 *
	 * @throws Exception
	 */
	@Test
	public void registerTest() throws Exception {
		RegisterDTO regDTO=new RegisterDTO();
		regDTO.setEmailId("test1@gmail.com");
		regDTO.setPassword("12345678");
		regDTO.setMobile("9164480831");
		regDTO.setName("user1");
		Optional<RegisterUser> already = Optional.of(user);
		when(userRepository.findByEmailId(user.getEmailId()) != null)
				.thenThrow(new RegistrationException("email is already registered"));
		when(modelmapper.map(regDTO, RegisterUser.class)).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(regDTO.getEmailId(), already.get().getEmailId());
	}

	/**
	 * to test the login api
	 */
	@Test
	public void loginTest() {

		userDTO.setEmailId("test1@gmail.com");
		userDTO.setPassword("12345678");
		Optional<RegisterUser> already = Optional.of(user);
		when(userRepository.findByEmailId(user.getEmailId())).thenReturn(user);
		when(passwordEncoder.matches(userDTO.getPassword(), user.getPassword())).thenReturn(true);
		assertEquals(userDTO.getEmailId(), already.get().getEmailId());
	}

}