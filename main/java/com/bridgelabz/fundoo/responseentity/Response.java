package com.bridgelabz.fundoo.responseentity;

import org.apache.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
	private org.springframework.http.HttpStatus statusCode;
	private Object data;
	private String message;
}
