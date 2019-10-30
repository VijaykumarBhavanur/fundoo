package com.bridgelabz.fundoo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.exception.LoginException;
import com.bridgelabz.fundoo.exception.RegistrationException;
import com.bridgelabz.fundoo.exception.UnautorizedException;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.util.TokenUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	IRegisterRepository regRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public Response validateCredentials(String email, String password) {
		RegisterUser user = regRepository.findByEmailId(email);
		boolean result = bCryptPasswordEncoder.matches(password, user.getPassword());

		if (result && user != null)
			return new Response(200, null, "Login sucess....");

		throw new LoginException("Invalid Credentials");
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

		return new Response(200, null, "success");

	}

	public Response sendEmail(String email, String token) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Testing Mail API");
		mail.setText("Use this token to change password===>  " + token);
		javaMailSender.send(mail);
		return new Response(200, null, "Mail sent successfully...");

	}

	@Override
	public Response resetPassword(String token, String newpassword) {
		String email = TokenUtil.decodeToken(token);
		if (email != null) {
			RegisterUser user = regRepository.findByEmailId(email);
			user.setPassword(new BCryptPasswordEncoder().encode(newpassword));
			regRepository.save(user);
			return new Response(200, null, "success");
		}

		throw new UnautorizedException("Unautorized");
	}

	@Override
	public String getJWTToken(String email) {
		return TokenUtil.getJWTToken(email);
	}

}
