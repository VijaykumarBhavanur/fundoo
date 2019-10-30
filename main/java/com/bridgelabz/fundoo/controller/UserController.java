package com.bridgelabz.fundoo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Response> login(@RequestHeader String emailId, @RequestHeader String password)
	{
		Response response=userService.validateCredentials(emailId, password);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody @Valid RegisterDTO regdto) 
	{
		Response response=userService.registerUser(regdto);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@GetMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestHeader String email) {
		String token = userService.getJWTToken(email);
		Response response=userService.sendEmail(email, token);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

	@PutMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token, @RequestHeader String newPassword) {
		Response response= userService.resetPassword(token, newPassword);
			return new ResponseEntity<Response>(response,HttpStatus.OK);
	}

}
