package com.bridgelabz.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

		@ExceptionHandler(LoginException.class)
		public ResponseEntity<ErrorResponse>loginException(Exception ex)
		{
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(401,null,ex.getMessage()),HttpStatus.OK);
		}
		
		
		@ExceptionHandler(RegistrationException.class)
		public ResponseEntity<ErrorResponse>registrationException(Exception ex)
		{
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(400,null,ex.getMessage()),HttpStatus.OK);
		}
		
		
		@ExceptionHandler(UnautorizedException.class)
		public ResponseEntity<ErrorResponse>unautorizedException(Exception ex)
		{
			return new ResponseEntity<ErrorResponse>(new ErrorResponse(400,null,ex.getMessage()),HttpStatus.OK);
		}
}
