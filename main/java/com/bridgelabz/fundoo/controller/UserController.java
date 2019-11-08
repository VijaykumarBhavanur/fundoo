package com.bridgelabz.fundoo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.RegisterDTO;
import com.bridgelabz.fundoo.model.RegisterUser;
import com.bridgelabz.fundoo.responseentity.Response;
import com.bridgelabz.fundoo.service.IUserService;
import com.bridgelabz.fundoo.util.TokenUtil;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	/**
	 * Purpose: For user login
	 * 
	 * @param emailId
	 * @param password
	 * @return response with "success" message or LoginException
	 */
	@GetMapping("/login")
	public ResponseEntity<Response> login(@RequestHeader String emailId, @RequestHeader String password) {
		Response response = userService.validateCredentials(emailId, password);
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	/**
	 * Purpose: For User Registration and to sent token
	 * 
	 * @param regdto
	 * @return response with "success" message or RegistrationException
	 */
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody RegisterDTO regdto) {
		Response response = userService.registerUser(regdto);
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	/**
	 * Purpose: For forgot password
	 * 
	 * @param email
	 * @return response with "token sent to email sent successfully" message or
	 *         UnautorizedException
	 */
	@GetMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestHeader String email) {
		Response response = userService.forgotPassword(email);
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	/**
	 * Purpose:To verify registered user
	 * 
	 * @param token
	 * @return response with "OK" message or UnautorizedException
	 */

	@PutMapping("/verify")
	public ResponseEntity<Response> verifyUser(@RequestHeader String token) {

		return new ResponseEntity<>(userService.verifyUser(TokenUtil.decodeToken(token)), HttpStatus.OK);
	}

	/**
	 * Purpose:To reset password
	 * 
	 * @param token
	 * @param newPassword
	 * @return response with "OK" status code
	 */

	@PutMapping("/resetPassword")
	public ResponseEntity<Response> resetPassword(@RequestHeader String token, @RequestHeader String newPassword) {
		Response response = userService.resetPassword(token, newPassword);
		return new ResponseEntity<>(response, response.getStatusCode());
	}

	/**
	 * Purpose:To get all registered user
	 * 
	 * @return response with list of registered users or UnautorizedException
	 */
	@GetMapping("/users")
	public ResponseEntity<List<RegisterUser>> getAllUsers() {
		List<RegisterUser> list = userService.getUsers();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * Purpose:To add profile-pic
	 * 
	 * @param multipart file
	 * @param token
	 * @return response with "OK" status code or UnautorizedException
	 */
	@PostMapping("/profilepic")
	public ResponseEntity<Response> addProfile(@RequestParam("image") MultipartFile file, @RequestParam String token)
			throws Exception {
		Response response = userService.saveProfilePic(file, TokenUtil.decodeToken(token));

		return new ResponseEntity<>(response, response.getStatusCode());
	}

	/**
	 * Purpose:To delete profile-pic
	 * 
	 * @param token
	 * @return response with "OK" status code or UnautorizedException
	 */
	@DeleteMapping("/profilepic")
	public ResponseEntity<Response> deleteProfile(@RequestParam String token) {
		Response response = userService.deleteProfilePic(TokenUtil.decodeToken(token));

		return new ResponseEntity<>(response, response.getStatusCode());
	}

	/**
	 * Purpose:To update profile-pic
	 * 
	 * @param multipart file
	 * @param token
	 * @return response with "OK" status code or UnautorizedException
	 */
	@PutMapping("/profilepic")
	public ResponseEntity<Response> updateProfile(@RequestParam("image") MultipartFile file, @RequestParam String token)
			throws IOException {
		Response response = userService.updateProfilePic(file, TokenUtil.decodeToken(token));
		return new ResponseEntity<>(response, response.getStatusCode());
	}

}