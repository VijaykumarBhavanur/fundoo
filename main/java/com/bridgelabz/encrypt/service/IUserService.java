package com.bridgelabz.encrypt.service;


import com.bridgelabz.encrypt.dto.RegisterDTO;
import com.bridgelabz.encrypt.model.RegisterUser;

public interface IUserService 
{
	//public void saveUser(UserDTO udto);
	public boolean validateCredentials(String email,String pass);
	public String  registerUser(RegisterDTO regdto);
	public RegisterUser getUserByEmail(String email);
	public String getJWTToken(String email);
	public void sendEmail(String email,String token);
	public String resetPassword(String token, String newpassword);
	public String getEmailFromToken(String token);
}
