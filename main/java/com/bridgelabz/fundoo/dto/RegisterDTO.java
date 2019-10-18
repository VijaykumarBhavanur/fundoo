package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterDTO {
	@NotNull(message = "Email can't be null")
	@Email
	private String emailId;

	@NotNull(message = "Name can't be null")
	@Pattern(regexp = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")
	private String name;
	
	@Size(min = 10, max = 10, message = "mobile number should be of 10 digits starting from 6,7,8,9")
	@Pattern(regexp = "(^[6-9]{1}[0-9]{9}$)")
	private String mobile;

	@Size(min = 8, max = 8, message = "password should be 8 characters with atleast one lowercase,"
			+ "one uppercase,one digit, one special symbol ")
	@Pattern(regexp = "(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$)")
	private String password;

}
