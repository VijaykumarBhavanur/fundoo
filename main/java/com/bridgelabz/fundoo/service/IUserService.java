package com.bridgelabz.fundoo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.responseentity.Response;

public interface IUserService {
	public Response validateCredentials(String email, String pass);

	public Response registerUser(RegisterDTO regdto);

	public Response sendEmail(String email, String token);

	public Response forgotPassword(String email);
	public Response resetPassword(String token, String newpassword);

	public String getJWTToken(String email);
	
	public Response saveProfilePic(MultipartFile file,String emailId)throws Exception;
	
	public Response deleteProfilePic(String emailId);
	
	public Response updateProfilePic(MultipartFile file,String emailId)throws IOException ;
	
	public List<RegisterUser> getUsers();
	
	public Response verifyUser(String token);
}
