package com.bridgelabz.fundoo.service;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.responseentity.Response;

public interface IUserService {
	public Response validateCredentials(String email, String pass);

	public Response registerUser(RegisterDTO regdto);

	public Response sendEmail(String email, String token);

	public Response resetPassword(String token, String newpassword);

	public String getJWTToken(String email);
}
