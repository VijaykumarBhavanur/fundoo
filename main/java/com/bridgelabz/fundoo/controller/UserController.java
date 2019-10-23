package com.bridgelabz.fundoo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService userService;

	@GetMapping("/login")
	public Response login(@RequestHeader String emailId, @RequestHeader String password) {
		if (userService.validateCredentials(emailId, password))
			return new Response(200, null, "Login success...");
		else
			return new Response(401, null, "Invalid Credentials...");
	}

	@PostMapping("/register")
	public Response register(@RequestBody @Valid RegisterDTO regdto) {
		if (userService.registerUser(regdto).contentEquals("success"))
			return new Response(200, null, "Registration success for user: " + regdto.getName());
		else
			return new Response(407, null, userService.registerUser(regdto));
	}

	@GetMapping("/forgotPassword")
	public Response forgotPassword(@RequestHeader String email) {
		String token = userService.getJWTToken(email);
		userService.sendEmail(email, token);
		return new Response(200, null, "Token sent to email successfully....");
	}

	@PutMapping("/resetPassword")
	public Response resetPassword(@RequestHeader String token, @RequestHeader String newPassword) {
		System.out.println("Got new password from postman:::::::::" + newPassword);
		String reset = userService.resetPassword(token, newPassword);
		if (reset.contentEquals("success"))
			return new Response(200, null, "New password set successfully....");
		else
			return new Response(401, null, reset);
	}

}
