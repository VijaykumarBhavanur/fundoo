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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController 
{
	
	@Autowired
	IUserService userService;
	

	
	@GetMapping("/login")
	public ResponseEntity<String>login(@RequestHeader String emailId,@RequestHeader String password)
	{
		if(userService.validateCredentials(emailId,password))
			return new ResponseEntity<String>("Login succcess.....",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Failed to login...Invalid Credentials...", HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String>register(@Valid @RequestBody  RegisterDTO regdto)
	{
			if(userService.registerUser(regdto).contentEquals("success"))
			return new ResponseEntity<String>("Successfully registered....", HttpStatus.OK);
			else
				return new ResponseEntity<String>(userService.registerUser(regdto), HttpStatus.OK);
	}
	
	@GetMapping("/getUser")
	public ResponseEntity<String>register(@RequestParam String email)
	{
		if(userService.getUserByEmail(email)!=null)
		return new ResponseEntity<String>(userService.getUserByEmail(email).getName(),HttpStatus.OK);
		else
			return new ResponseEntity<String>("No User found!!!",HttpStatus.OK);
	}
	
	
	
	@GetMapping("/getToken")
	public ResponseEntity<String>getToken(@RequestParam String email)
	{
		String token=userService.getJWTToken(email);
		userService.sendEmail(email,token);
		return new ResponseEntity<String>("Token sent to email successfully....",HttpStatus.OK);
		
	}
	
	@GetMapping("/getEmailByToken")
	public ResponseEntity<String>getEmail(@RequestParam String token)
	{
		String email=userService.getEmailFromToken(token);
		if(email!=null)
			return new ResponseEntity<String>(email,HttpStatus.OK);
		else
			return new ResponseEntity<String>("Invalid token",HttpStatus.OK);
	}
	
	@GetMapping("/forgotPassword")
	public ResponseEntity<String>forgotPassword(@RequestParam String email)
	{
		String token=userService.getJWTToken(email);
		userService.sendEmail(email,token);
		return new ResponseEntity<String>("Token sent to email successfully....",HttpStatus.OK);
	}
	
	
	@PutMapping("/resetPassword")
	public ResponseEntity<String>resetPassword(@RequestHeader String token, @RequestHeader String newPassword)
	{
		System.out.println("Got new password from postman:::::::::"+newPassword);
		String reset=userService.resetPassword(token, newPassword);
		if(reset.contentEquals("success"))
			return new ResponseEntity<String>("New password set successfully...",HttpStatus.OK);
		else
			return new ResponseEntity<String>(reset,HttpStatus.OK);
	}
	
}
