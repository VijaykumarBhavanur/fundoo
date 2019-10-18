package com.bridgelabz.fundoo.service;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.repository.IRegisterRepository;


@Service
public class UserServiceImpl implements IUserService
{
	
	@Autowired
	IRegisterRepository regRepository;

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	ModelMapper modelMapper;
	
	public final String TOKEN_SECRET="BridgeLabz";


	public boolean validateCredentials(String email,String password)
	{
		RegisterUser user=regRepository.findByEmailId(email);
		
		System.out.println("Found user by emailId:::"+user);
		
		
		System.out.println("Matching password returns...."+new BCryptPasswordEncoder().matches(password,user.getPassword()));
		
		return new BCryptPasswordEncoder().matches(password,user.getPassword());
	}
	
	
	public RegisterUser getUserByEmail(String email)
	{
		return regRepository.findByEmailId(email);
	}
	
	public String  registerUser(RegisterDTO regdto)
	{
		System.out.println("Registering user by email::::"+regdto.getEmailId());
		if(regRepository.findByEmailId(regdto.getEmailId())!=null)
		{
			return "EmailId already exist!!";
		}
		
		if(regRepository.findByMobile(regdto.getMobile())!=null)
		{
			return "Mobile number already exist!!";
		} 
		
		
		RegisterUser regUser= modelMapper.map(regdto, RegisterUser.class);
		
	     System.out.println(regUser);
		
		regRepository.save(regUser);
		
		return "success";
		
	}

	public void sendEmail(String email,String token) 
	{
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email);
		mail.setSubject("Testing Mail API");
		mail.setText("Use this token to change password===>  "+token);
		javaMailSender.send(mail);
		
	}
	
	
	@Override
	public String getJWTToken(String email) 
	{
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

			String token = JWT.create().withClaim("emailId",email).sign(algorithm);
				return token;
			}
		catch (Exception e) 
		{
			System.out.println("Unable to create JWT Token");
		}
		return null;
	}
	

	
	public String getEmailFromToken(String token)
	{
		
		String email=decodeToken(token);
		return email;
		
	}

	
	public String decodeToken(String token) 
	{
		Verification verification = null;
		try {
		verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
		} catch (IllegalArgumentException e) 
		{
		e.printStackTrace();
		}
		JWTVerifier jwtverifier = verification.build();
		DecodedJWT decodedjwt = jwtverifier.verify(token);

		Claim claim = decodedjwt.getClaim("emailId");
		if(claim==null)
			return null;
		return claim.asString();
		}


	@Override
	public String resetPassword(String token, String newpassword)
	{
		String email=decodeToken(token);
		System.out.println("Decoded email:::"+email);
		if(email!=null)
		{
			RegisterUser user=regRepository.findByEmailId(email);
			System.out.println("Found User::::"+user.getName());
			System.out.println("setting new password::::::"+newpassword);
			user.setPassword(new BCryptPasswordEncoder().encode(newpassword));
			regRepository.save(user);
			return "success";
		}
		
		return "Invalid token.....";
	}
	
}
		
