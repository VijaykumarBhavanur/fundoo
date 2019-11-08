package com.bridgelabz.fundoo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.exception.UnautorizedException;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.util.TokenUtil;
import com.bridgelabz.fundoo.util.Utility;

@Service
@EnableCaching
public class UserServiceImpl implements IUserService {

	@Autowired
	private IRegisterRepository regRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	public Response validateCredentials(String email, String password) {
		if (email.isEmpty() || password.isEmpty())
			throw new LoginException("Please enter both fields!!");
		RegisterUser user = regRepository.findByEmailId(email);
		if (user == null)
			throw new LoginException("Invalid EmailId");
		boolean result = bCryptPasswordEncoder.matches(password, user.getPassword());

		if (result && user.isVerified())
			return new Response(HttpStatus.OK, null,"Login sucess");
		throw new UnautorizedException("Unauthorized User");
	}

	public Response registerUser(RegisterDTO regdto) {
		if (regRepository.findByEmailId(regdto.getEmailId()) != null) {
			throw new RegistrationException("EmailId already exist!!");
		}

		if (regRepository.findByMobile(regdto.getMobile()) != null) {
			throw new RegistrationException("Mobile number already exist!!");
		}

		RegisterUser regUser = modelMapper.map(regdto, RegisterUser.class);
		regUser.setPassword(bCryptPasswordEncoder.encode(regdto.getPassword()));

		sendEmail(regdto.getEmailId(), TokenUtil.getJWTToken(regdto.getEmailId()));

		regRepository.save(regUser);

		return new Response(HttpStatus.OK, null, "success");

	}

	public Response sendEmail(String email, String token) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Testing Mail API");
		mail.setText("Use this token to change password ===>  " + token);
		javaMailSender.send(mail);
		return new Response(HttpStatus.OK, null, "Mail sent successfully...");

	}

	@Override
	public Response resetPassword(String token, String newpassword) {
		String email = TokenUtil.decodeToken(token);
		if (email != null) {
			RegisterUser user = regRepository.findByEmailId(email);
			user.setPassword(new BCryptPasswordEncoder().encode(newpassword));
			regRepository.save(user);
			return new Response(HttpStatus.OK, null, "success");
		}

		throw new UnautorizedException("Unautorized");
	}

	@Override
	public String getJWTToken(String email) {
		return TokenUtil.getJWTToken(email);
	}

	@Override
	public Response saveProfilePic(MultipartFile file, String emailId) throws Exception {
		RegisterUser user = regRepository.findByEmailId(emailId);
		if (user == null)
			throw new UnautorizedException("Unautorized User");

		byte[] bytes = file.getBytes();
		String extension = file.getContentType().replace("image/", "");
		String fileLocation = Utility.PROFILE_PIC_LOCATION + emailId + "." + extension;
		Path path = Paths.get(fileLocation);
		Files.write(path, bytes);

		user.setProfilePic(fileLocation);
		regRepository.save(user);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
	}

	@Override
	public List<RegisterUser> getUsers() {
		return regRepository.findAll();
	}

	@Override
	public Response deleteProfilePic(String emailId) {
		RegisterUser register = regRepository.findByEmailId(emailId);
		if (register == null)
			throw new UnautorizedException("Unautorized User");

		String fileLocation = register.getProfilePic();
		File file = new File(fileLocation);
		file.delete();
		register.setProfilePic("");
		regRepository.save(register);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
	}

	public Response updateProfilePic(MultipartFile file, String emailId) throws IOException {

		RegisterUser register = regRepository.findByEmailId(emailId);
		if (register == null)
			throw new UnautorizedException("Unautorized User");

		byte[] bytes = file.getBytes();
		String extension = file.getContentType().replace("image/", "");
		String fileLocation = Utility.PROFILE_PIC_LOCATION + emailId + "." + extension;
		Path path = Paths.get(fileLocation);
		Files.write(path, bytes);

		register.setProfilePic(fileLocation);
		regRepository.save(register);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);

	}

	@Override
	public Response verifyUser(String email) {
		RegisterUser user = regRepository.findByEmailId(email);
		if (user == null)
			throw new UnautorizedException("Unautorized user");
		user.setVerified(true);
		regRepository.save(user);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);
	}

	@Override
	public Response forgotPassword(String email) {
		RegisterUser user = regRepository.findByEmailId(email);
		if (user == null)
			throw new UnautorizedException("Unautorized user");

		String token = getJWTToken(email);
		Response response = sendEmail(email, token);
		return new Response(HttpStatus.OK, null, Utility.RECORD_UPDATED);

	}

}
