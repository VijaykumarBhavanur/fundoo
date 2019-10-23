package com.bridgelabz.fundoo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;
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

	public boolean validateCredentials(String email, String password) {
		RegisterUser user = regRepository.findByEmailId(email);

		System.out.println("Found user by emailId:::" + user);

		System.out
				.println("Matching password returns...." + bCryptPasswordEncoder.matches(password, user.getPassword()));

		return bCryptPasswordEncoder.matches(password, user.getPassword());
	}

	public RegisterUser getUserByEmail(String email) {
		return regRepository.findByEmailId(email);
	}

	public String registerUser(RegisterDTO regdto) {
		System.out.println("Registering user by email::::" + regdto.getEmailId());
		if (regRepository.findByEmailId(regdto.getEmailId()) != null) {
			return "EmailId already exist!!";
		}

		if (regRepository.findByMobile(regdto.getMobile()) != null) {
			return "Mobile number already exist!!";
		}

		RegisterUser regUser = modelMapper.map(regdto, RegisterUser.class);
		regUser.setPassword(bCryptPasswordEncoder.encode(regdto.getPassword()));

		System.out.println(regUser);

		sendEmail(regdto.getEmailId(), TokenUtil.getJWTToken(regdto.getEmailId()));

		regRepository.save(regUser);

		return "success";

	}

	public void sendEmail(String email, String token) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Testing Mail API");
		mail.setText("Use this token to change password===>  " + token);
		javaMailSender.send(mail);

	}



	@Override
	public String resetPassword(String token, String newpassword) {
		String email = TokenUtil.decodeToken(token);
		System.out.println("Decoded email:::" + email);
		if (email != null) {
			RegisterUser user = regRepository.findByEmailId(email);
			System.out.println("Found User::::" + user.getName());
			System.out.println("setting new password::::::" + newpassword);
			user.setPassword(new BCryptPasswordEncoder().encode(newpassword));
			regRepository.save(user);
			return "success";
		}

		return "Invalid token.....";
	}

	@Override
	public String getJWTToken(String email)
	{
		return TokenUtil.getJWTToken(email);
	}

}
